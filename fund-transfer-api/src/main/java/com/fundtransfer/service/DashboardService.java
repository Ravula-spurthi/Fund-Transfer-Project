package com.fundtransfer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fundtransfer.dto.DashboardDTO;
import com.fundtransfer.entity.User;
import com.fundtransfer.repository.TransactionRepository;
import com.fundtransfer.repository.UserRepository;

@Service
public class DashboardService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    public DashboardDTO getDashboard(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String lastTransaction = "No Transactions";

        return new DashboardDTO(
                user.getName(),
                user.getAccountNumber(),
                user.getBalance(),
                lastTransaction
        );
    }
}