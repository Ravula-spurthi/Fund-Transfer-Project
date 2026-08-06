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

    // ==========================
    // Get All Transactions
    // ==========================
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    // ==========================
    // Get Transaction By ID
    // ==========================
    public Transaction getTransactionById(Long id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));
    }

    // ==========================
    // Get User Transactions
    // ==========================
    public List<Transaction> getTransactionsByUser(Long userId) {
        return transactionRepository.findByUserId(userId);
    }

    // ==========================
    // Statement
    // ==========================
    public List<Transaction> getStatement(
            Long userId,
            LocalDate fromDate,
            LocalDate toDate) {

        if (fromDate != null && toDate != null) {

            return transactionRepository.findByUserIdAndTransactionDateBetween(
                    userId,
                    fromDate,
                    toDate);

        }

        return transactionRepository.findByUserId(userId);

    }

    // ==========================
    // ADMIN FILTER - USER
    // ==========================
    public List<Transaction> searchByUser(String userName) {

        return transactionRepository
                .findBySenderNameContainingIgnoreCaseOrReceiverNameContainingIgnoreCase(
                        userName,
                        userName);

    }

    // ==========================
    // ADMIN FILTER - DATE
    // ==========================
    public List<Transaction> filterByDate(
            LocalDate fromDate,
            LocalDate toDate) {

        return transactionRepository
                .findByTransactionDateBetween(
                        fromDate,
                        toDate);

    }

}