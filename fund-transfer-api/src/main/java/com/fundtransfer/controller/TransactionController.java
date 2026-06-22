package com.fundtransfer.controller;
 
import java.util.List;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
 
import com.fundtransfer.entity.Transaction;
import com.fundtransfer.repository.TransactionRepository;
 
@RestController
public class TransactionController {
 
    @Autowired
    private TransactionRepository transactionRepository;
 
    @GetMapping("/transactions")
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }
}