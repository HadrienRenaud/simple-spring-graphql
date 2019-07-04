package com.sipios.projectgraphqlhadrien;

import com.sipios.projectgraphqlhadrien.repository.MediaRepository;
import com.sipios.projectgraphqlhadrien.repository.UserRepository;
import graphql.schema.DataFetcher;
import org.springframework.stereotype.Component;

@Component
public class GraphQLDataFetchers {
    private final UserRepository userRepository;
    private final MediaRepository mediaRepository;

    public GraphQLDataFetchers(UserRepository userRepository, MediaRepository mediaRepository) {
        this.userRepository = userRepository;
        this.mediaRepository = mediaRepository;
    }

    DataFetcher getMediasDataFetcher() {
        return dataFetchingEnvironment -> mediaRepository.findAll();
    }
}
