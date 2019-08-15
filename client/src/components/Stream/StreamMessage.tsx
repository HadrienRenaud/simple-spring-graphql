import React from 'react';

import { Message } from './Stream'

interface StreamMessageProps {
  message: Message
}

export function StreamMessage ({ message }: StreamMessageProps) {
  return (
    <div>
      <h4>
        {message.author
          ? <>{message.author.username} sent</>
          : <>A new message was sent</>
        }:
      </h4>
      <p>
        {message.content}
      </p>
      <em>
        ({message.createdAt.toLocaleString()})
      </em>
    </div>
  )
}
