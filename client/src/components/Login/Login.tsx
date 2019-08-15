import React from "react";

import gql from "graphql-tag";
import { useApolloClient } from "@apollo/react-hooks";

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
  id?: string
  username?: string
  email?: string
}

export interface LoginProps {
  onUserGot: (user: User) => void
}

export function Login({ onUserGot }: LoginProps) {
  const client = useApolloClient();
  let input: HTMLInputElement | null;

  return (
    <div>
      <h2>Please enter your user id</h2>
      <form onSubmit={async (e) => {
        e.preventDefault()
        if (input) {
          const { data } = await client.query({
            query: GET_USER,
            variables: {
              userId: input.value
            }
          })
          onUserGot(data.user)
        }
      }}>
        <input ref={node => (input = node)} type="number" />
        <button type="submit">Get in !</button>
      </form>
    </div>
  );
}
