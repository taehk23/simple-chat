package com.taehk23.simplechat.dto;

public record UpdateMessageRequest(
        Long authorId,
        String content
) {
}
