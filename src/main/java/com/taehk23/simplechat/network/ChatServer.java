package com.taehk23.simplechat.network;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.taehk23.simplechat.dto.ChatRequest;
import com.taehk23.simplechat.dto.CreateMessageRequest;
import com.taehk23.simplechat.dto.DeleteMessageRequest;
import com.taehk23.simplechat.dto.DeleteMessageResponse;
import com.taehk23.simplechat.dto.FindMessageRequest;
import com.taehk23.simplechat.dto.UpdateMessageRequest;
import com.taehk23.simplechat.service.MessageService;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {

    private final int port;
    private final MessageService messageService;
    private final ObjectMapper mapper;

    public ChatServer(int port, MessageService messageService) {
        this.port = port;
        this.messageService = messageService;
        this.mapper = new ObjectMapper()
                .findAndRegisterModules()
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started on port " + port);

            while(true) {
                try(Socket socket = serverSocket.accept()) {
                    System.out.println("Client connected");

                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(socket.getInputStream())
                    );
                    BufferedWriter writer = new BufferedWriter(
                            new OutputStreamWriter(socket.getOutputStream())
                    );

                    String line = reader.readLine();
                    ChatRequest rq = mapper.readValue(line, ChatRequest.class);

                    Object response;
                    try {
                        response = handleRequest(rq);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                    String responseJson = mapper.writeValueAsString(response);
                    writer.write(responseJson);
                    writer.newLine();
                    writer.flush();

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Object handleRequest(ChatRequest chatRequest) throws IOException {
        return switch (chatRequest.type()) {
            case SEND -> {
                CreateMessageRequest rq = mapper.readValue(chatRequest.body(), CreateMessageRequest.class);
                yield messageService.send(rq);
            }

            case LOAD -> {
                FindMessageRequest rq = mapper.readValue(chatRequest.body(), FindMessageRequest.class);
                yield messageService.load(rq);
            }

            case MODIFY -> {
                UpdateMessageRequest rq = mapper.readValue(chatRequest.body(), UpdateMessageRequest.class);
                yield messageService.modify(rq);
            }

            case DELETE ->  {
                DeleteMessageRequest rq = mapper.readValue(chatRequest.body(), DeleteMessageRequest.class);
                messageService.delete(rq);
                yield new DeleteMessageResponse("Message deleted succesfully.");
            }
        };
    }
}
