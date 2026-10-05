package com.gusthavomnz.core_api.repository;

import com.gusthavomnz.core_api.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByUserIdAndImageFileNameIsNotNull(Long userId);
}
