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
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@GraphQLApi
@Component
public class MessageService {
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final Set<FluxSink<MessageModel>> subscriptions = ConcurrentHashMap.newKeySet();

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
        MessageModel saveMessage = messageRepository.save(message);
        subscriptions.forEach(subscriber -> subscriber.next(message));
        return saveMessage;
    }

    @GraphQLSubscription(name = "newMessages")
    public Publisher<MessageModel> subscribeToNewMessages() {
        return Flux.create(subscriber -> subscriptions.add(subscriber.onDispose(() -> subscriptions.remove(subscriber))), FluxSink.OverflowStrategy.LATEST);
    }
}
