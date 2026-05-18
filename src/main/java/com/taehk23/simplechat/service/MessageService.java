package com.taehk23.simplechat.service;

import com.taehk23.simplechat.dto.CreateMessageRequest;
import com.taehk23.simplechat.dto.DeleteMessageRequest;
import com.taehk23.simplechat.dto.FindMessageRequest;
import com.taehk23.simplechat.dto.MessageResponse;
import com.taehk23.simplechat.dto.UpdateMessageRequest;
import com.taehk23.simplechat.entity.Message;
import com.taehk23.simplechat.repository.FileMessageRepository;

import java.util.List;

public class MessageService {
    private final FileMessageRepository messageRepo;

    public MessageService(FileMessageRepository fileMessageRepo) {
        this.messageRepo = fileMessageRepo;
    }

    public synchronized MessageResponse send(CreateMessageRequest rq) {
        Message message = new Message(
                rq.content(),
                rq.authorId()
        );

        messageRepo.save(message);
        return messageMapper(message);
    }

    public List<MessageResponse> load(FindMessageRequest rq) {
        return messageRepo.findLastMessages(rq.size()).stream()
                .map(this::messageMapper)
                .toList();
    }

    public MessageResponse modify(UpdateMessageRequest rq) {
        Message message = messageRepo.findById(rq.id())
                .orElseThrow(() -> new RuntimeException("Message with id " + rq.id() + " not found"));

        if(!message.getAuthorId().equals(rq.authorId())) {
            throw new RuntimeException("Only the author can modify this message.");
        }

        message.update(rq.content());
        messageRepo.save(message);
        return messageMapper(message);
    }

    public void delete(DeleteMessageRequest rq) {
        Message message = messageRepo.findById(rq.id())
                .orElseThrow(() -> new RuntimeException("Message with id " + rq.id() + " not found"));

        if(!message.getAuthorId().equals(rq.authorId())) {
            throw new RuntimeException("Only the author can delete this message.");
        }

        messageRepo.deleteById(rq.id());
    }

    private MessageResponse messageMapper(Message message) {
        return new MessageResponse(
                message.getId(),
                message.getContent(),
                message.getAuthorId(),
                message.getCreatedAt()
        );
    }
}
