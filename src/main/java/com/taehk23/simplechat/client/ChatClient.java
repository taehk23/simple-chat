package com.taehk23.simplechat.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.taehk23.simplechat.dto.ChatRequest;
import com.taehk23.simplechat.dto.CreateMessageRequest;
import com.taehk23.simplechat.dto.DeleteMessageRequest;
import com.taehk23.simplechat.dto.FindMessageRequest;
import com.taehk23.simplechat.dto.UpdateMessageRequest;
import com.taehk23.simplechat.network.RequestType;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ChatClient {

    private final String host;
    private final int port;
    private final ObjectMapper mapper;

    public ChatClient(String host, int port) {
        this.host = host;
        this.port = port;
        this.mapper = new ObjectMapper()
                .findAndRegisterModules()
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    public void send(Long authorId, String content) {
        CreateMessageRequest body = new CreateMessageRequest(authorId, content);
        request(RequestType.SEND, body);
    }

    public void load(int size) {
        FindMessageRequest body = new FindMessageRequest(size);
        request(RequestType.LOAD, body);
    }

    public void modify(Long id, Long authorId, String content) {
        UpdateMessageRequest body = new UpdateMessageRequest(id, authorId, content);
        request(RequestType.MODIFY, body);
    }

    public void delete(Long id, Long authorId) {
        DeleteMessageRequest body = new DeleteMessageRequest(id, authorId);
        request(RequestType.DELETE, body);
    }

    private void request(RequestType type, Object body) {
        try (Socket socket = new Socket("localhost", 8080);
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream())
            );
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
            )){

            String bodyJson = mapper.writeValueAsString(body);
            ChatRequest rq = new ChatRequest(type, bodyJson);
            String rqJson = mapper.writeValueAsString(rq);

            writer.write(rqJson);
            writer.newLine();
            writer.flush();

            String response = reader.readLine();
            System.out.println("Server Response: " + response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
