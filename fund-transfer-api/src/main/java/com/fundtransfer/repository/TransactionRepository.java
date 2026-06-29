package com.fundtransfer.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fundtransfer.entity.Transaction;

public interface TransactionRepository
        extends JpaRepository<Transaction, Long> {
List<Transaction> findByUserId(Long userId);
}