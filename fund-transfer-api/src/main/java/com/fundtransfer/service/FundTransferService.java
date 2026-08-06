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

        User sender = userRepository
                .findByAccountNumber(dto.getSenderAccount())
                .orElseThrow(() -> new RuntimeException("Sender not found"));

        User beneficiary = userRepository
                .findByAccountNumber(dto.getReceiverAccount())
                .orElseThrow(() -> new RuntimeException("Beneficiary not found"));

        if (sender.getId().equals(beneficiary.getId())) {
            throw new RuntimeException("Cannot transfer to the same account");
        }

        if (dto.getAmount() == null || dto.getAmount() <= 0) {
            throw new RuntimeException("Amount must be greater than 0");
        }

        if (dto.getAmount() > 50000) {
            throw new RuntimeException("Daily transfer limit exceeded");
        }

        if (sender.getBalance() < dto.getAmount()) {
            throw new RuntimeException("Insufficient balance");
        }

        // Update balances
        sender.setBalance(sender.getBalance() - dto.getAmount());
        beneficiary.setBalance(beneficiary.getBalance() + dto.getAmount());

        userRepository.save(sender);
        userRepository.save(beneficiary);

        LocalDate transactionDate;

        if (dto.getScheduleDate() != null && !dto.getScheduleDate().isEmpty()) {
            transactionDate = LocalDate.parse(dto.getScheduleDate());
        } else {
            transactionDate = LocalDate.now();
        }

        // ==========================
        // Sender Transaction (DEBIT)
        // ==========================

        Transaction debitTransaction = new Transaction();

        debitTransaction.setUserId(sender.getId());
        debitTransaction.setSenderName(sender.getName());
        debitTransaction.setReceiverName(beneficiary.getName());

        debitTransaction.setFromAccount(sender.getAccountNumber());
        debitTransaction.setToAccount(beneficiary.getAccountNumber());

        debitTransaction.setBeneficiaryName(beneficiary.getName());

        debitTransaction.setAmount(dto.getAmount());
        debitTransaction.setStatus("SUCCESS");
        debitTransaction.setTransactionDate(transactionDate);

        // Direction
        debitTransaction.setTransactionType("DEBIT");

        // Payment Type (Pay Now / Pay Later)
        debitTransaction.setTransactionMode(dto.getPaymentType());

        debitTransaction.setRemarks(dto.getRemarks());
        debitTransaction.setBalance(sender.getBalance());

        transactionRepository.save(debitTransaction);

        // ==========================
        // Receiver Transaction (CREDIT)
        // ==========================

        Transaction creditTransaction = new Transaction();

        creditTransaction.setUserId(beneficiary.getId());
        creditTransaction.setSenderName(sender.getName());
        creditTransaction.setReceiverName(beneficiary.getName());

        creditTransaction.setFromAccount(sender.getAccountNumber());
        creditTransaction.setToAccount(beneficiary.getAccountNumber());

        creditTransaction.setBeneficiaryName(sender.getName());

        creditTransaction.setAmount(dto.getAmount());
        creditTransaction.setStatus("SUCCESS");
        creditTransaction.setTransactionDate(transactionDate);

        // Direction
        creditTransaction.setTransactionType("CREDIT");

        // Payment Type (Pay Now / Pay Later)
        creditTransaction.setTransactionMode(dto.getPaymentType());

        creditTransaction.setRemarks(dto.getRemarks());
        creditTransaction.setBalance(beneficiary.getBalance());

        transactionRepository.save(creditTransaction);

        return "Fund Transfer Successful";
    }
}