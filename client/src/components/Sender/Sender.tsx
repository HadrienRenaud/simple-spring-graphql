import React from "react";

import gql from "graphql-tag";
import { useMutation } from "@apollo/react-hooks";

const POST_MESSAGE = gql`
  mutation sendMessage($content: String!, $authorId: Int!) {
    newMessage(content: $content, author: { id: $authorId }) {
      id
      author {
        id
        username
      }
      createdAt
      content
    }
  }
`;

export interface Author {
  id?: string;
  username?: string;
  email?: string;
}

export interface SenderProps {
  author?: Author;
}

export function Sender({ author }: SenderProps) {
  let input: HTMLInputElement | null;
  const [newMessage, { loading, error }] = useMutation(POST_MESSAGE);

  const authorId = (author && author.id) || 1;
  const authorUsername = (author && author.username) || "Hadrien";

  return (
    <div>
      <h2>Send a message as {authorUsername}</h2>
      <form
        onSubmit={e => {
          e.preventDefault();
          if (input) {
            newMessage({
              variables: {
                authorId: authorId,
                content: input.value || ""
              }
            });
            input.value = "";
          }
        }}
      >
        <input
          ref={node => {
            input = node;
          }}
        />
        <button type="submit" disabled={loading}>
          {loading ? <>Loading ...</> : <>Send message !</>}
        </button>
        {error && <>{error.message}</>}
      </form>
    </div>
  );
}
