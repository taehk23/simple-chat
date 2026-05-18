package com.taehk23.simplechat.app;

import com.taehk23.simplechat.network.ChatClient;

public class ClientApp {
    public static void main(String[] args) {
        new ChatClient().send("hello");
    }
}
