package com.fundtransfer.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fundtransfer.dto.FundTransferDTO;
import com.fundtransfer.entity.Transaction;
import com.fundtransfer.entity.User;
import com.fundtransfer.repository.TransactionRepository;
import com.fundtransfer.repository.UserRepository;

@Service
public class FundTransferService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    public String transferFunds(FundTransferDTO dto) {

        // Find sender using account number
        User sender = userRepository.findByAccountNumber(dto.getSenderAccount())
                .orElseThrow(() -> new RuntimeException("Sender not found"));

        // Find beneficiary using account number
        User beneficiary = userRepository.findByAccountNumber(dto.getReceiverAccount())
                .orElseThrow(() -> new RuntimeException("Beneficiary not found"));

        // Validation: same account
        if (sender.getAccountNumber().equals(beneficiary.getAccountNumber())) {
            throw new RuntimeException("Cannot transfer to same account");
        }

        // Validation: amount
        if (dto.getAmount() == null || dto.getAmount() <= 0) {
            throw new RuntimeException("Amount must be greater than 0");
        }

        // Validation: daily limit
        if (dto.getAmount() > 50000) {
            throw new RuntimeException("Daily transfer limit exceeded");
        }

        // Validation: balance
        if (sender.getBalance() < dto.getAmount()) {
            throw new RuntimeException("Insufficient Balance");
        }

        // Update balances
        sender.setBalance(sender.getBalance() - dto.getAmount());
        beneficiary.setBalance(beneficiary.getBalance() + dto.getAmount());

        userRepository.save(sender);
        userRepository.save(beneficiary);

        // Save transaction
        Transaction transaction = new Transaction();
        transaction.setUserId(sender.getId());
        transaction.setBeneficiaryName(beneficiary.getName());
        transaction.setAmount(dto.getAmount());
        transaction.setStatus("SUCCESS");
        transaction.setTransactionDate(LocalDate.now());
        transaction.setTransactionType("IMPS");
        transaction.setRemarks(dto.getRemarks());
        transaction.setBalance(sender.getBalance());
        transaction.setTransactionMode("DEBIT");

        transactionRepository.save(transaction);

        return "Fund Transfer Successful";
    }
}