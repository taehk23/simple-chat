package com.taehk23.simplechat.app;

import com.taehk23.simplechat.network.ChatServer;

public class ServerApp {
    public static void main(String[] args) {
        new ChatServer(8080).start();
    }
}
