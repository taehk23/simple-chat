package com.taehk23.simplechat.client;

public class ClientApp {
    public static void main(String[] args) {
        ChatClient client = new ChatClient("localhost", 8080);

        client.send(1L, "hello");
        client.load(50);
        client.modify(1L, 1L, "modified hello");
        client.load(50);
        client.delete(1L, 1L);
        client.load(50);
    }
}
