import React from "react";

import { Message } from "./Stream";
import Card from "@material-ui/core/Card";
import Typography from "@material-ui/core/Typography";
import CardContent from "@material-ui/core/CardContent";

interface StreamMessageProps {
  message: Message;
}

export function StreamMessage({ message }: StreamMessageProps) {
  return (
    <Card style={{ marginTop: 32 }}>
      <CardContent>
        <Typography variant="body1">{message.content}</Typography>
        <Typography variant="subtitle2">
          {message.author && <>{message.author.username} - </>}
          {new Date(message.createdAt).toLocaleString()}
        </Typography>
      </CardContent>
    </Card>
  );
}
