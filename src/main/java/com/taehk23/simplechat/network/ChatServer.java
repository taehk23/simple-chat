package com.taehk23.simplechat.network;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taehk23.simplechat.dto.ChatRequest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLOutput;

public class ChatServer {

    private final int port;

    public ChatServer(int port) {
        this.port = port;
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started on port " + port);

            Socket socket = serverSocket.accept();
            System.out.println("Client connected");

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
            );
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream())
            );

            String line = reader.readLine();
            System.out.println("Server received line: " + line);

            ObjectMapper objectMapper = new ObjectMapper();
            ChatRequest rq = objectMapper.readValue(line, ChatRequest.class);
            System.out.println(rq.type());
            System.out.println(rq.body());

            writer.write("received: " + line);
            writer.newLine();
            writer.flush();
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }
}
