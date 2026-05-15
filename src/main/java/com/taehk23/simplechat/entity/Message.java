package com.taehk23.simplechat.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
public class Message {

    @Setter
    private Long id;

    private String content;
    private final Long authorId;
    private final Long receiverId;
    private final Instant createdAt;
    private Instant updatedAt;
    private boolean deletedOnAuthor;
    private boolean deletedOnReceiver;

    public Message(String content, Long authorId, Long receiverId) {
        this.content = content;
        this.authorId = authorId;
        this.receiverId = receiverId;
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
        this.deletedOnAuthor = false;
        this.deletedOnReceiver = false;
    }

    public void update(String content) {
        this.content = content;
        this.updatedAt = Instant.now();
    }

    public void deleteOnAuthor() {
        this.deletedOnAuthor = true;
    }

    public void deleteOnReceiver() {
        this.deletedOnReceiver = true;
    }
}
