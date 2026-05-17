package com.taehk23.simplechat.dto;

import java.time.Instant;

public record MessageResponse(
        Long id,
        String content,
        Long authorId,
        Instant createdAt
) {
}
