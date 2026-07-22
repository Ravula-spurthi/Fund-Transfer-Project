package com.fundtransfer.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fundtransfer.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

<<<<<<< HEAD
    // Existing methods
=======
>>>>>>> 00975106c91753255d8b01489de6196ecb9b56c0
    List<Transaction> findByUserId(Long userId);

    List<Transaction> findByUserIdAndTransactionDateBetween(
            Long userId,
            LocalDate fromDate,
            LocalDate toDate);

<<<<<<< HEAD
    // Statement page
=======
>>>>>>> 00975106c91753255d8b01489de6196ecb9b56c0
    List<Transaction> findByUserIdOrderByTransactionDateDesc(Long userId);

    List<Transaction> findByUserIdAndTransactionDateOrderByTransactionDateDesc(
            Long userId,
            LocalDate transactionDate);
<<<<<<< HEAD

    // NEW - Monthly Chart
    List<Transaction> findByUserIdOrderByTransactionDateAsc(Long userId);

=======
>>>>>>> 00975106c91753255d8b01489de6196ecb9b56c0
}