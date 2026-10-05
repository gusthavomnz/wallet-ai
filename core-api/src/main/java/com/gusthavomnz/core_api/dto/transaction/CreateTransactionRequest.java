package com.gusthavomnz.core_api.dto.transaction;

import com.gusthavomnz.core_api.entity.TransactionOrigin;
import com.gusthavomnz.core_api.entity.TransactionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CreateTransactionRequest(
        @NotNull Long userId,
        Long categoryId,
        @NotBlank String description,
        @NotNull @Positive BigDecimal amount,
        @NotNull TransactionType type,
        @NotNull LocalDate transactionDate,
        @NotNull TransactionOrigin origin,
        String imageFileName
) {}
