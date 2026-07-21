package com.fundtransfer.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fundtransfer.dto.ScheduledTransferDTO;
import com.fundtransfer.entity.ScheduledTransfer;
import com.fundtransfer.repository.ScheduledTransferRepository;

@Service
public class ScheduledTransferService {

    @Autowired
    private ScheduledTransferRepository repository;

    // Save Pay Later Transfer
    public ScheduledTransfer saveTransfer(ScheduledTransferDTO dto) {

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

    // Get Only Pending Transfers
    public List<ScheduledTransfer> getPendingTransfers() {
        return repository.findByStatus("PENDING");
    }

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

        transfer.setStatus("COMPLETED");

        return repository.save(transfer);
    }

}