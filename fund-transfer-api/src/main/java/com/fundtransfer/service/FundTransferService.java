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

        User sender = userRepository.findById(dto.getSenderId())
                .orElseThrow(() -> new RuntimeException("Sender not found"));

        User beneficiary = userRepository.findById(dto.getBeneficiaryId())
                .orElseThrow(() -> new RuntimeException("Beneficiary not found"));

        // Debug
        System.out.println("Sender Balance: " + sender.getBalance());
        System.out.println("Beneficiary Balance: " + beneficiary.getBalance());

        if (sender.getBalance() == null) {
            sender.setBalance(0.0);
        }

        if (beneficiary.getBalance() == null) {
            beneficiary.setBalance(0.0);
        }

        if (sender.getId().equals(beneficiary.getId())) {
            throw new RuntimeException("Cannot transfer to the same account");
        }

        if (dto.getAmount() <= 0) {
            throw new RuntimeException("Amount must be greater than 0");
        }

        if (dto.getAmount() > 50000) {
            throw new RuntimeException("Daily transfer limit exceeded");
        }

        if (sender.getBalance() < dto.getAmount()) {
            throw new RuntimeException("Insufficient balance");
        }

        sender.setBalance(sender.getBalance() - dto.getAmount());
        beneficiary.setBalance(beneficiary.getBalance() + dto.getAmount());

        userRepository.save(sender);
        userRepository.save(beneficiary);

        Transaction transaction = new Transaction();

        transaction.setUserId(sender.getId());
        transaction.setBeneficiaryName(beneficiary.getName());
        transaction.setAmount(dto.getAmount());
        transaction.setStatus("SUCCESS");
        transaction.setTransactionDate(
                dto.getTransferDate() != null ? dto.getTransferDate() : LocalDate.now());

        transaction.setTransactionType(dto.getTransferType());
        transaction.setRemarks(dto.getRemarks());

        transaction.setBalance(sender.getBalance());
        transaction.setTransactionMode("DEBIT");

        transactionRepository.save(transaction);

        return "Fund Transfer Successful";
    }
}