package com.fundtransfer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fundtransfer.dto.DashboardDTO;
import com.fundtransfer.entity.User;
import com.fundtransfer.repository.UserRepository;

@Service
public class DashboardService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionPinService transactionPinService;

    public DashboardDTO getDashboard(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        DashboardDTO dto = new DashboardDTO();

        dto.setName(user.getName());
        dto.setAccountNumber(user.getAccountNumber());
        dto.setBalance(user.getBalance());

        return dto;
    }

    public Double getBalance(Long userId, String transactionPin) {

        boolean valid = transactionPinService.verifyPin(userId, transactionPin);

        if (!valid) {
            throw new RuntimeException("Invalid Transaction PIN");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return user.getBalance();
    }
}