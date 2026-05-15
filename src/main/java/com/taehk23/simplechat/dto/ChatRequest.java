package com.taehk23.simplechat.dto;

import com.fasterxml.jackson.databind.JsonNode;

public record ChatRequest(
        String type,
        JsonNode body
) {
}
