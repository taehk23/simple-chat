package com.taehk23.simplechat.network;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taehk23.simplechat.dto.ChatRequest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ChatClient {

    public void send(String message) {
        try (Socket socket = new Socket("localhost", 8080)) {
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream())
            );
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
            );

            ObjectMapper objectMapper = new ObjectMapper();

            ChatRequest rq = new ChatRequest(
                    RequestType.SEND,
                    "hello"
            );
            String json = objectMapper.writeValueAsString(rq);

            writer.write(json);
            writer.newLine();
            writer.flush();

            String response = reader.readLine();
            System.out.println("Server Response: " + response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
