package com.fundtransfer.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.BAD_REQUEST, "Sender not found"));

        User beneficiary = userRepository
                .findByAccountNumber(dto.getReceiverAccount())
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.BAD_REQUEST, "Beneficiary not found"));

        if (sender.getTransactionPin() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Transaction PIN not set");
        }

        if (!sender.getTransactionPin().equals(dto.getTransactionPin())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Invalid Transaction PIN");
        }

        if (sender.getId().equals(beneficiary.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Cannot transfer to the same account");
        }

        if (dto.getAmount() == null || dto.getAmount() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Amount must be greater than 0");
        }

        if (dto.getAmount() > 50000) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Daily transfer limit exceeded");
        }

        if (sender.getBalance() < dto.getAmount()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Insufficient Balance");
        }

        sender.setBalance(sender.getBalance() - dto.getAmount());
        beneficiary.setBalance(beneficiary.getBalance() + dto.getAmount());

        userRepository.save(sender);
        userRepository.save(beneficiary);

        Transaction transaction = new Transaction();

        transaction.setUserId(sender.getId());
        transaction.setBeneficiaryName(dto.getBeneficiaryName());
        transaction.setAmount(dto.getAmount());
        transaction.setStatus("SUCCESS");

        if (dto.getScheduleDate() != null && !dto.getScheduleDate().isEmpty()) {
            transaction.setTransactionDate(LocalDate.parse(dto.getScheduleDate()));
        } else {
            transaction.setTransactionDate(LocalDate.now());
        }

        transaction.setTransactionType(dto.getPaymentType());
        transaction.setRemarks(dto.getRemarks());
        transaction.setBalance(sender.getBalance());
        transaction.setTransactionMode("DEBIT");

        transactionRepository.save(transaction);

        return "Fund Transfer Successful";
    }
}