import React, { useState } from "react";

import gql from "graphql-tag";
import { useApolloClient } from "@apollo/react-hooks";
import Card from "@material-ui/core/Card";
import CardActions from "@material-ui/core/CardActions";
import CardContent from "@material-ui/core/CardContent";
import Typography from "@material-ui/core/Typography";
import Button from "@material-ui/core/Button";
import TextField from "@material-ui/core/TextField";

const GET_USER = gql`
  query getUser($userId: Int!) {
    user(id: $userId) {
      id
      username
      email
    }
  }
`;

export interface User {
  id?: string;
  username?: string;
  email?: string;
}

export interface LoginProps {
  onUserGot: (user: User) => void;
}

export function Login({ onUserGot }: LoginProps) {
  const client = useApolloClient();
  const [input, setInput] = useState<String>("");

  return (
    <Card>
      <CardContent>
        <Typography variant="h3">Please enter your user id</Typography>
      </CardContent>
      <form
        onSubmit={async e => {
          e.preventDefault();
          if (input) {
            const { data } = await client.query({
              query: GET_USER,
              variables: {
                userId: input
              }
            });
            onUserGot(data.user);
          }
        }}
      >
        <CardContent>
          <TextField
            type="number"
            value={input}
            onChange={e => {
              e.preventDefault();
              setInput(e.target.value);
            }}
          />
        </CardContent>
        <CardActions>
          <Button type="submit">Get in !</Button>
        </CardActions>
      </form>
    </Card>
  );
}
