package com.gusthavomnz.core_api.dto.transaction;

import com.gusthavomnz.core_api.entity.TransactionOrigin;
import com.gusthavomnz.core_api.entity.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record TransactionResponse(
        Long id,
        Long userId,
        Long categoryId,
        String description,
        BigDecimal amount,
        TransactionType type,
        LocalDate transactionDate,
        TransactionOrigin origin,
        String imageUrl,
        LocalDateTime createdAt
) {}
