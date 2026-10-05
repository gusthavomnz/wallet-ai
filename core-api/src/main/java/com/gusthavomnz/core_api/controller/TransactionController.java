package com.gusthavomnz.core_api.controller;

import com.gusthavomnz.core_api.dto.transaction.CreateTransactionRequest;
import com.gusthavomnz.core_api.dto.transaction.TransactionResponse;

import java.util.List;
import com.gusthavomnz.core_api.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TransactionResponse create(@Valid @RequestBody CreateTransactionRequest request) {
        return transactionService.create(request);
    }

    @GetMapping("/{id}")
    public TransactionResponse findById(@PathVariable Long id) {
        return transactionService.findById(id);
    }

    @GetMapping("/user/{userId}/images")
    public List<TransactionResponse> findImagesByUser(@PathVariable Long userId) {
        return transactionService.findImagesByUser(userId);
    }
}
