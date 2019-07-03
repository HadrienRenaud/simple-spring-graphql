package com.sipios.projectgraphqlhadrien;

import com.google.common.collect.ImmutableMap;
import graphql.schema.DataFetcher;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
public class GraphQLDataFetchers {
    private static List<Map<String, String>> users = Arrays.asList(
            ImmutableMap.of(
                    "id", "1",
                    "username", "hadrien",
                    "email", "hadrienr@sipios.com"
            ),
            ImmutableMap.of(
                    "id", "2",
                    "username", "raphael",
                    "email", "raphaelm@sipios.com"
            ),
            ImmutableMap.of(
                    "id", "1",
                    "username", "michael",
                    "email", "michaelm@sipios.com"
            )
    );

    public DataFetcher getUserByIdDataFetcher() {
        return dataFetchingEnvironment -> {
            String userId = dataFetchingEnvironment.getArgument("id");
            return users.stream()
                    .filter(user -> user.get("id").equals(userId))
                    .findFirst()
                    .orElse(null);
        };
    }

    public DataFetcher getUserByUsernameDataFetcher() {
        return dataFetchingEnvironment -> {
            String username = dataFetchingEnvironment.getArgument("username");
            return users.stream()
                    .filter(user -> user.get("username").equals(username))
                    .findFirst()
                    .orElse(null);
        };
    }

    public DataFetcher getUsers() {
        return dataFetchingEnvironment -> {
            return users;
        };
    }
}
