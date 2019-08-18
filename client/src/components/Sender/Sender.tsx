import React, { useState } from "react";

import gql from "graphql-tag";
import { useMutation } from "@apollo/react-hooks";
import {
  Card,
  Typography,
  TextField,
  Button,
  CardContent,
  CardActions
} from "@material-ui/core";

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
  const [newMessage, { loading, error }] = useMutation(POST_MESSAGE);
  const [input, setInput] = useState<String>("");

  const authorId = (author && author.id) || 1;
  const authorUsername = (author && author.username) || "Hadrien";

  return (
    <Card>
      <form
        onSubmit={e => {
          e.preventDefault();
          if (input) {
            newMessage({
              variables: {
                authorId: authorId,
                content: input
              }
            });
            setInput("");
          }
        }}
      >
        <CardContent>
          <Typography variant="h3">
            Send a message as {authorUsername}
          </Typography>
        </CardContent>
        <CardContent>
          <TextField
            value={input}
            onChange={e => {
              e.preventDefault();
              setInput(e.target.value);
            }}
            error={!!error}
            helperText={error && error.message}
            fullWidth
          />
        </CardContent>
        <CardActions>
          <Button type="submit" disabled={loading}>
            {loading ? <>Loading ...</> : <>Send message !</>}
          </Button>
        </CardActions>
      </form>
    </Card>
  );
}
