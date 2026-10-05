package com.gusthavomnz.core_api.service;

import com.gusthavomnz.core_api.dto.transaction.CreateTransactionRequest;
import com.gusthavomnz.core_api.dto.transaction.TransactionResponse;

import java.util.List;
import com.gusthavomnz.core_api.entity.Category;
import com.gusthavomnz.core_api.entity.Transaction;
import com.gusthavomnz.core_api.mapper.TransactionMapper;
import com.gusthavomnz.core_api.port.S3StoragePort;
import com.gusthavomnz.core_api.repository.TransactionRepository;
import com.gusthavomnz.core_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private static final int PRESIGNED_URL_EXPIRATION_MINUTES = 15;

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final S3StoragePort s3StoragePort;
    private final TransactionMapper transactionMapper;

    @Transactional
    public TransactionResponse create(CreateTransactionRequest request) {
        Transaction transaction = transactionMapper.toEntity(request);

        transaction.setUser(userRepository.findById(request.userId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")));

        if (request.categoryId() != null) {
            Category category = new Category();
            category.setId(request.categoryId());
            transaction.setCategory(category);
        }

        return toResponse(transactionRepository.save(transaction));
    }

    @Transactional(readOnly = true)
    public TransactionResponse findById(Long id) {
        return transactionRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Transaction not found"));
    }

    @Transactional(readOnly = true)
    public List<TransactionResponse> findImagesByUser(Long userId) {
        return transactionRepository.findByUserIdAndImageFileNameIsNotNull(userId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private TransactionResponse toResponse(Transaction transaction) {
        String imageUrl = null;

        if (transaction.getImageFileName() != null) {
            imageUrl = s3StoragePort.generateTempFileLink(transaction.getImageFileName(), PRESIGNED_URL_EXPIRATION_MINUTES);
        }

        return transactionMapper.toResponse(transaction, imageUrl);
    }
}
