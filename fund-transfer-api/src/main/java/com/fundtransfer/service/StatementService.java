package com.fundtransfer.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fundtransfer.entity.Transaction;
import com.fundtransfer.repository.TransactionRepository;

@Service
public class StatementService {

    @Autowired
    private TransactionRepository transactionRepository;

    public List<Transaction> getStatement(
            Long userId,
            LocalDate transactionDate) {

        // If a date is selected, return transactions only for that date
        if (transactionDate != null) {
            return transactionRepository
                    .findByUserIdAndTransactionDateOrderByTransactionDateDesc(
                            userId,
                            transactionDate);
        }

        // Otherwise return all transactions
        return transactionRepository
                .findByUserIdOrderByTransactionDateDesc(userId);
    }
}