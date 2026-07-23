package com.fundtransfer.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fundtransfer.dto.ScheduledTransferDTO;
import com.fundtransfer.entity.ScheduledTransfer;
import com.fundtransfer.entity.Transaction;
import com.fundtransfer.entity.User;
import com.fundtransfer.repository.ScheduledTransferRepository;
import com.fundtransfer.repository.TransactionRepository;
import com.fundtransfer.repository.UserRepository;
@Service
public class ScheduledTransferService {

    @Autowired
    private ScheduledTransferRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionPinService transactionPinService;

    @Autowired
    private TransactionRepository transactionRepository;

    // Save Pay Later Transfer
    public ScheduledTransfer saveTransfer(ScheduledTransferDTO dto) {

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

        boolean validPin = transactionPinService.verifyPin(
                sender.getId(),
                dto.getTransactionPin());

        if (!validPin) {
            throw new RuntimeException("Invalid Transaction PIN");
        }

        ScheduledTransfer transfer = new ScheduledTransfer();

        transfer.setSenderAccount(dto.getSenderAccount());
        transfer.setReceiverAccount(dto.getReceiverAccount());
        transfer.setBeneficiaryName(dto.getBeneficiaryName());
        transfer.setAmount(dto.getAmount());
        transfer.setRemarks(dto.getRemarks());
        transfer.setPaymentType(dto.getPaymentType());
        transfer.setScheduleDate(LocalDate.parse(dto.getScheduleDate()));
        transfer.setTransactionPin(dto.getTransactionPin());
        transfer.setUserId(dto.getUserId());
        transfer.setStatus("PENDING");

        return repository.save(transfer);
    }

    // Get All Scheduled Transfers
    public List<ScheduledTransfer> getAllTransfers() {
        return repository.findAll();
    }

    // Get Pending Scheduled Transfers
    public List<ScheduledTransfer> getPendingTransfers() {
        return repository.findByStatus("PENDING");
    }

    // Get Logged-in User Transfers
    public List<ScheduledTransfer> getTransfersByUser(Long userId) {
        return repository.findByUserId(userId);
    }

    // Update Scheduled Transfer
    public ScheduledTransfer updateTransfer(Long id,
                                            ScheduledTransferDTO dto) {

        ScheduledTransfer transfer = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Transfer Not Found"));

        transfer.setBeneficiaryName(dto.getBeneficiaryName());
        transfer.setReceiverAccount(dto.getReceiverAccount());
        transfer.setAmount(dto.getAmount());
        transfer.setRemarks(dto.getRemarks());
        transfer.setScheduleDate(LocalDate.parse(dto.getScheduleDate()));

        return repository.save(transfer);
    }

    // Delete Scheduled Transfer
    public void deleteTransfer(Long id) {
        repository.deleteById(id);
    }

   // Execute Scheduled Transfer
public ScheduledTransfer executeTransfer(Long id) {

    ScheduledTransfer transfer = repository.findById(id)
            .orElseThrow(() ->
                    new RuntimeException("Transfer Not Found"));

    if ("COMPLETED".equalsIgnoreCase(transfer.getStatus())) {
        throw new RuntimeException("Transfer already executed");
    }

    User sender = userRepository
            .findByAccountNumber(transfer.getSenderAccount())
            .orElseThrow(() ->
                    new RuntimeException("Sender not found"));

    User beneficiary = userRepository
            .findByAccountNumber(transfer.getReceiverAccount())
            .orElseThrow(() ->
                    new RuntimeException("Beneficiary not found"));

    if (sender.getBalance() < transfer.getAmount()) {
        throw new RuntimeException("Insufficient balance");
    }

    // Debit Sender
    sender.setBalance(sender.getBalance() - transfer.getAmount());

    // Credit Beneficiary
    beneficiary.setBalance(
            beneficiary.getBalance() + transfer.getAmount());

    userRepository.save(sender);
    userRepository.save(beneficiary);

    // Create Transaction History
    Transaction transaction = new Transaction();

    transaction.setUserId(sender.getId());
    transaction.setBeneficiaryName(
            transfer.getBeneficiaryName());
    transaction.setAmount(
            transfer.getAmount());
    transaction.setStatus("SUCCESS");
    transaction.setTransactionDate(LocalDate.now());
    transaction.setTransactionType("Pay Later");
    transaction.setRemarks(
            transfer.getRemarks());
    transaction.setBalance(sender.getBalance());
    transaction.setTransactionMode("DEBIT");

    transactionRepository.save(transaction);

    // Update Scheduled Transfer Status
    transfer.setStatus("COMPLETED");

    return repository.save(transfer);
}
}