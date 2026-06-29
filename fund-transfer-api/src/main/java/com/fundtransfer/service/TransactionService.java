package com.fundtransfer.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fundtransfer.entity.Transaction;
import com.fundtransfer.repository.TransactionRepository;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    public Transaction saveTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }
    public Transaction getTransactionById(Long id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));
    }

    // ADD THIS
    public List<Transaction> getTransactionsByUser(Long userId) {
        return transactionRepository.findByUserId(userId);
    }

    public List<Transaction> getStatement(LocalDate fromDate, LocalDate toDate)
     {
         return transactionRepository.findByTransactionDateBetween(fromDate, toDate);
     }
}