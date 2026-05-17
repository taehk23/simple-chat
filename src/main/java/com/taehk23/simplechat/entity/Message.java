package com.taehk23.simplechat.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
public class Message {

    @Setter
    private Long id;

    private String content;
    private Long authorId;
    private Instant createdAt;

    public Message(String content, Long authorId) {
        this.content = content;
        this.authorId = authorId;
        this.createdAt = Instant.now();
    }

    public Message() {
        this.authorId = null;
        this.createdAt = null;
    }

    public void update(String content) {
        this.content = content;
    }
}
