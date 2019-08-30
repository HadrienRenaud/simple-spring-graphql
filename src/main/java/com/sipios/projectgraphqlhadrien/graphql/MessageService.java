package com.sipios.projectgraphqlhadrien.graphql;

import com.sipios.projectgraphqlhadrien.models.MessageModel;
import com.sipios.projectgraphqlhadrien.models.UserModel;
import com.sipios.projectgraphqlhadrien.repository.MessageRepository;
import com.sipios.projectgraphqlhadrien.repository.UserRepository;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@GraphQLApi
@Component
public class MessageService {
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

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

    @GraphQLMutation(name = "deleteAllMessages")
    public List<MessageModel> deleteAllMessages() {
        messageRepository.deleteAll();
        return allMessages();
    }

    @GraphQLMutation(name = "newMessage")
    public MessageModel newMessage(
            @GraphQLArgument(name = "content") String content,
            @GraphQLArgument(name = "author") UserModel author
    ) {
        MessageModel message = new MessageModel(content, userRepository.getById(author.getId()).orElse(null));
        MessageModel saveMessage = messageRepository.save(message);
        return saveMessage;
    }
}
