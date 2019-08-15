package com.sipios.projectgraphqlhadrien.services;

import com.sipios.projectgraphqlhadrien.models.MessageModel;
import com.sipios.projectgraphqlhadrien.models.UserModel;
import com.sipios.projectgraphqlhadrien.repository.MessageRepository;
import com.sipios.projectgraphqlhadrien.repository.UserRepository;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.annotations.GraphQLSubscription;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import org.reactivestreams.Publisher;
import com.sipios.projectgraphqlhadrien.utils.SubscriberManager;
import org.springframework.web.bind.annotation.CrossOrigin;
import reactor.core.publisher.Flux;
import org.springframework.stereotype.Component;
import reactor.core.publisher.FluxSink;

import java.util.List;

@GraphQLApi
@Component
public class MessageService {
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final SubscriberManager<String, FluxSink<MessageModel>> subscribers = new SubscriberManager<>();

    public MessageService(MessageRepository messageRepository, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
    }

    @GraphQLQuery(name = "message")
    public MessageModel getById(@GraphQLArgument(name = "id") Integer id) {
        return messageRepository.findById(id).orElse(null);
    }

    @GraphQLQuery(name = "allMessages")
    public List<MessageModel> allMessages() {
        return messageRepository.findAllByOrderByCreatedAtDesc();
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GraphQLMutation(name = "newMessage")
    public MessageModel newMessage(
            @GraphQLArgument(name = "content") String content,
            @GraphQLArgument(name = "author") UserModel author
    ) {
        MessageModel message = new MessageModel(content, userRepository.getById(author.getId()).orElse(null));
        subscribers.get("all").forEach(subscriber -> subscriber.next(message));
        return messageRepository.save(message);
    }

    @GraphQLSubscription(name = "newMessages")
    public Publisher<MessageModel> subscribeToNewMessages() {
        String code = "all";
        return Flux.create(subscriber -> subscribers.add(code, subscriber.onDispose(() -> subscribers.remove(code, subscriber))), FluxSink.OverflowStrategy.LATEST);
    }
}
