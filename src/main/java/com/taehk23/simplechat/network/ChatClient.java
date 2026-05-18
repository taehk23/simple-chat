package com.taehk23.simplechat.network;

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

            writer.write(message);
            writer.newLine();
            writer.flush();

            String response = reader.readLine();
            System.out.println("Server Response: " + response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
