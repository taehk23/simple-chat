package com.taehk23.simplechat.dto;

import com.taehk23.simplechat.network.RequestType;

public record ChatRequest(
        RequestType type,
        String body
) {
}
