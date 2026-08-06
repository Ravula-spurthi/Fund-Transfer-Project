package com.fundtransfer.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fundtransfer.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    // User Transactions
    List<Transaction> findByUserId(Long userId);

    List<Transaction> findByUserIdAndTransactionDateBetween(
            Long userId,
            LocalDate fromDate,
            LocalDate toDate);

    List<Transaction> findByUserIdOrderByTransactionDateDesc(Long userId);

    List<Transaction> findByUserIdAndTransactionDateOrderByTransactionDateDesc(
            Long userId,
            LocalDate transactionDate);

    List<Transaction> findByUserIdOrderByTransactionDateAsc(Long userId);

    // ==========================
    // ADMIN FILTERS
    // ==========================

    List<Transaction> findBySenderNameContainingIgnoreCaseOrReceiverNameContainingIgnoreCase(
            String senderName,
            String receiverName);

    List<Transaction> findByTransactionDateBetween(
            LocalDate fromDate,
            LocalDate toDate);

}