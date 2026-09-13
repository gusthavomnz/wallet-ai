package com.gusthavomnz.core_api.dto.user;

import java.time.LocalDateTime;

public record UserResponse(
        Long id,
        String name,
        String email,
        LocalDateTime createdAt
) {}
