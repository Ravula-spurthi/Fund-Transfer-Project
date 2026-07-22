package com.fundtransfer.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fundtransfer.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    // Existing methods
    List<Transaction> findByUserId(Long userId);

    List<Transaction> findByUserIdAndTransactionDateBetween(
            Long userId,
            LocalDate fromDate,
            LocalDate toDate);

    // Statement page
    List<Transaction> findByUserIdOrderByTransactionDateDesc(Long userId);

    List<Transaction> findByUserIdAndTransactionDateOrderByTransactionDateDesc(
            Long userId,
            LocalDate transactionDate);

    // NEW - Monthly Chart
    List<Transaction> findByUserIdOrderByTransactionDateAsc(Long userId);

}