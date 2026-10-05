package com.gusthavomnz.core_api.dto.chat;

public record ChatResponse(
        String produto,
        Double preco,
        String tag,
        String imageFileName
) {}
