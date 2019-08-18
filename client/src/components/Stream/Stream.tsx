import React from "react";
import { useQuery } from "@apollo/react-hooks";
import { ApolloError } from "apollo-client";
import gql from "graphql-tag";

import { StreamMessage } from "./StreamMessage";

const LIST_MESSAGES = gql`
  query allMessages {
    allMessages {
      id
      content
      createdAt
      author {
        id
        username
      }
    }
  }
`;

const STREAM_MESSAGES = gql`
  subscription newMessage {
    newMessages {
      id
      content
      createdAt
      author {
        id
        username
      }
    }
  }
`;

export interface Message {
  id?: number;
  content: string;
  createdAt: Date;
  author?: {
    username: string;
  };
}

interface QueryData {
  allMessages: Array<Message>;
}

interface SubscriptionData {
  newMessages: Message;
}

interface DisplayProps {
  messages: Array<Message>;
  loading: boolean;
  error?: ApolloError;
  subscribeToNewMessages: () => void;
}

export class DisplayMessages extends React.Component<DisplayProps> {
  componentDidMount() {
    this.props.subscribeToNewMessages();
  }

  render = () => {
    const { messages, loading, error } = this.props;
    return (
      <>
        {messages.map((message, index) => (
          <StreamMessage key={index} message={message} />
        ))}
        {loading && <p>Loading ...</p>}
        {error && <p>Error: {error.message}</p>}
      </>
    );
  };
}

export function Stream() {
  const { loading, error, data, subscribeToMore } = useQuery<QueryData>(
    LIST_MESSAGES
  );

  let messages: Array<Message> = [];
  if (data && data.allMessages) {
    messages = data.allMessages.sort((m1: Message, m2: Message) => {
      const d1 = new Date(m1.createdAt).getTime();
      const d2 = new Date(m2.createdAt).getTime();
      return d2 - d1;
    });
  }

  return (
    <DisplayMessages
      loading={loading}
      error={error}
      messages={messages}
      subscribeToNewMessages={() =>
        subscribeToMore<SubscriptionData>({
          document: STREAM_MESSAGES,
          updateQuery: (prev, { subscriptionData }) => {
            if (subscriptionData.data) {
              const newMessage = subscriptionData.data.newMessages;
              return {
                ...prev,
                allMessages: [ newMessage, ...prev.allMessages ]
              };
            } else {
              return prev;
            }
          }
        })
      }
    />
  );
}
