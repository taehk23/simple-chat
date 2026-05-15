package com.taehk23.simplechat.dto;

public record DeleteMessageRequest(
        Long id,
        Long authorId
) {
}
