package com.fundtransfer.service;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fundtransfer.dto.DashboardDTO;
import com.fundtransfer.dto.MonthlyTransactionDTO;
import com.fundtransfer.entity.Transaction;
import com.fundtransfer.entity.User;
import com.fundtransfer.repository.TransactionRepository;
import com.fundtransfer.repository.UserRepository;

@Service
public class DashboardService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionRepository transactionRepository;

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

    // Monthly Transactions Summary
    public List<MonthlyTransactionDTO> getMonthlyTransactions(Long userId) {

        List<Transaction> transactions =
                transactionRepository.findByUserIdOrderByTransactionDateAsc(userId);

        // DEBUG
        System.out.println("==================================");
        System.out.println("User Id : " + userId);
        System.out.println("Transactions Found : " + transactions.size());

        for (Transaction transaction : transactions) {
            System.out.println(
                    "Amount = " + transaction.getAmount() +
                    ", Date = " + transaction.getTransactionDate() +
                    ", User = " + transaction.getUserId()
            );
        }
        System.out.println("==================================");

        double[] monthlyTotals = new double[12];

        for (Transaction transaction : transactions) {

            if (transaction.getTransactionDate() != null
                    && transaction.getAmount() != null) {

                int month = transaction.getTransactionDate().getMonthValue();

                monthlyTotals[month - 1] += transaction.getAmount();

            }

        }

        List<MonthlyTransactionDTO> result = new ArrayList<>();

        for (int i = 0; i < 12; i++) {

            result.add(
                    new MonthlyTransactionDTO(
                            Month.of(i + 1).name(),
                            monthlyTotals[i]
                    )
            );

        }

        return result;
    }
}