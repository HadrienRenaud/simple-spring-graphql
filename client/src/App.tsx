import React from 'react';

import { ApolloProvider } from '@apollo/react-hooks';
import { InMemoryCache } from 'apollo-boost';
import ApolloClient from 'apollo-client';
import { split } from 'apollo-link';
import { HttpLink } from 'apollo-link-http';
import { WebSocketLink } from 'apollo-link-ws';
import { getMainDefinition } from 'apollo-utilities';

import { Sender } from './components/Sender/Sender'
import { Stream } from './components/Stream/Stream';

// Create an http link:
const httpLink = new HttpLink({
  uri: 'http://localhost:8080/graphql'
});

// Create a WebSocket link:
const wsLink = new WebSocketLink({
  uri: `ws://localhost:8080/graphql`,
  options: {
    reconnect: true
  }
});

// using the ability to split links, you can send data to each link
// depending on what kind of operation is being sent
const link = split(
  // split based on operation type
  ({ query }) => {
    const definition = getMainDefinition(query);
    return (
      definition.kind === 'OperationDefinition' &&
      definition.operation === 'subscription'
    );
  },
  wsLink,
  httpLink,
);

const client = new ApolloClient({
    // Provide required constructor fields
    cache: new InMemoryCache(),
    link: link,
  });

const Footer = () => {
    return <>
        <p>This is Stream Line by Hadrien Renaud</p>
        <p>Check this out on <a href="https://github.com/HadrienRenaud/simple-spring-graphql">Github</a></p>
    </>
};

const App: React.FC = () => {
    return (
        <div className="App">
            <ApolloProvider client={client}>
                <Stream />
            </ApolloProvider>
        </div>
    );
}

export default App;
