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
            LocalDate fromDate,
            LocalDate toDate) {

        if (fromDate != null && toDate != null) {
            return transactionRepository
                    .findByUserIdAndTransactionDateBetween(
                            userId,
                            fromDate,
                            toDate);
        }

        return transactionRepository.findByUserId(userId);
    }
}