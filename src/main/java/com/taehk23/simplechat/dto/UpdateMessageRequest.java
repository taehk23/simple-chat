package com.taehk23.simplechat.dto;

public record UpdateMessageRequest(
        Long id,
        Long authorId,
        String content
) {
}
