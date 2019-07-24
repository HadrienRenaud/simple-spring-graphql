package com.sipios.projectgraphqlhadrien;

import com.sipios.projectgraphqlhadrien.models.MessageModel;
import com.sipios.projectgraphqlhadrien.models.UserModel;
import com.sipios.projectgraphqlhadrien.repository.MessageRepository;
import com.sipios.projectgraphqlhadrien.repository.UserRepository;
import graphql.schema.DataFetcher;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
public class GraphQLDataFetchers {
    private final UserRepository userRepository;
    private final MessageRepository messageRepository;


    public GraphQLDataFetchers(UserRepository userRepository, MessageRepository messageRepository) {
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
    }

    DataFetcher getMessagesDataFetcher() {
        return dataFetchingEnvironment -> messageRepository.findAll();
    }

    DataFetcher getMessageById() {
        return dataFetchingEnvironment -> messageRepository.findById(Integer.parseInt(dataFetchingEnvironment.getArgument("id")));
    }

    DataFetcher getUserById() {
        return dataFetchingEnvironment -> userRepository.getById(Integer.parseInt(dataFetchingEnvironment.getArgument("id")));
    }

    DataFetcher newMessage() {
        return environment -> {
            Optional<UserModel> optionalUser = userRepository.getById(Integer.parseInt(environment.getArgument("authorId")));
            if (!optionalUser.isPresent()) {
                return null;
            }
            UserModel user = optionalUser.get();
            MessageModel message = new MessageModel(environment.getArgument("content"), user);
            return messageRepository.save(message);
        };
    }

    DataFetcher newUser() {
        return dataFetchingEnvironment -> userRepository.save(new UserModel(
                dataFetchingEnvironment.getArgument("username"),
                dataFetchingEnvironment.getArgument("email")
        ));
    }
}
