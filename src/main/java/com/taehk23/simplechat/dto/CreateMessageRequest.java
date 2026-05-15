package com.taehk23.simplechat.dto;

public record CreateMessageRequest(
        Long authorId,
        String content
) {
}
