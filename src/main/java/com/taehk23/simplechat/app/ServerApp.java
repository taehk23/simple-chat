package com.taehk23.simplechat.app;

import com.taehk23.simplechat.network.ChatServer;
import com.taehk23.simplechat.repository.FileMessageRepository;
import com.taehk23.simplechat.service.MessageService;

public class ServerApp {
    public static void main(String[] args) {
        FileMessageRepository fileMessageRepo = new FileMessageRepository();
        MessageService messageService = new MessageService(fileMessageRepo);

        ChatServer server = new ChatServer(8080, messageService);
        server.start();
    }
}
