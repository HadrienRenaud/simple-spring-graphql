import React, { useState } from 'react';

import gql from 'graphql-tag';
import { useSubscription } from '@apollo/react-hooks';

import { StreamMessage } from './StreamMessage'

const STREAM_MESSAGES = gql`
subscription newMessage {
  newMessages {
    content
    createdAt
    author {
      username
    }
  }
}
`

export interface Message {
  content: string,
  createdAt: Date,
  author?: {
    username: string
  }
}

interface SubscriptionData {
  newMessages: Message
}

export function Stream() {
  const initialValue: Array<Message> = [{
    content: "Bienvenue !",
    createdAt: new Date()
  }];
  const [messages, setMessages] = useState(initialValue)
  const { loading, error} = useSubscription<SubscriptionData>(STREAM_MESSAGES, {
    onSubscriptionData: (hookArg) => {
      if (hookArg.subscriptionData.data) {
        setMessages([ ...messages, hookArg.subscriptionData.data.newMessages ])
      }
    }
  });

  return <>
    {messages.map((message, index) => (
      <StreamMessage key={index} message={message} />
    ))}
    {loading && <p>Loading ...</p>}
    {error && <p>Error: {error.message}</p>}
  </>;
}

