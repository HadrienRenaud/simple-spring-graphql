package com.sipios.projectgraphqlhadrien.services;

import com.sipios.projectgraphqlhadrien.models.UserModel;
import com.sipios.projectgraphqlhadrien.repository.UserRepository;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import org.springframework.stereotype.Service;

import java.util.List;

@GraphQLApi
@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GraphQLQuery(name = "user", description = "Get user from id")
    public UserModel getById(@GraphQLArgument(name = "id") Integer id) {
        return userRepository.getById(id).orElse(null);
    }

    @GraphQLQuery(name = "allUsers")
    public List<UserModel> allUsers() {
        return userRepository.findAll();
    }

    @GraphQLMutation(name = "signIn")
    public UserModel newUser(
            @GraphQLArgument(name="username") String username,
            @GraphQLArgument(name="email", defaultValue = "") String email
    ) {
        return userRepository.save(new UserModel(username, email));
    }
}
