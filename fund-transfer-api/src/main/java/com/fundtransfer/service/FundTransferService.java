package com.fundtransfer.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fundtransfer.dto.FundTransferDTO;
import com.fundtransfer.entity.Transaction;
import com.fundtransfer.repository.TransactionRepository;

@Service
public class FundTransferService {

    @Autowired
    private TransactionRepository transactionRepository;

    public String transferFunds(FundTransferDTO dto) {

        if (dto.getAmount() <= 0) {
            return "Invalid amount";
        }

        Transaction transaction = new Transaction();
        transaction.setBeneficiaryName(dto.getBeneficiaryName());
        transaction.setAmount(dto.getAmount());
        transaction.setStatus("SUCCESS");
        transaction.setTransactionDate(LocalDate.now());

        transactionRepository.save(transaction);

        return "Fund Transfer Successful";
    }
}