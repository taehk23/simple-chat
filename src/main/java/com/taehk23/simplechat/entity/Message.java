package com.taehk23.simplechat.entity;

import lombok.Getter;

import java.time.Instant;

@Getter
public class Message {

    private Long id;
    private String content;
    private final Long authorId;
    private final Instant createdAt;

    public Message(Long id, String content, Long authorId) {
        this.id = id;
        this.content = content;
        this.authorId = authorId;
        this.createdAt = Instant.now();
    }

    public void update(String content) {
        this.content = content;
    }
}
