 package com.fundtransfer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fundtransfer.dto.DashboardDTO;
import com.fundtransfer.dto.MonthlyTransactionDTO;
import com.fundtransfer.service.DashboardService;

@RestController
@RequestMapping("/dashboard")
@CrossOrigin(origins = "*")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    // Load dashboard details
    @GetMapping("/{userId}")
    public DashboardDTO getDashboard(@PathVariable Long userId) {
        return dashboardService.getDashboard(userId);
    }

    // Verify Transaction PIN and return balance
    @PostMapping("/balance")
    public Double getBalance(@RequestBody DashboardDTO dto) {

        return dashboardService.getBalance(
                dto.getUserId(),
                dto.getTransactionPin()
        );

    }

    // Monthly Transactions
    @GetMapping("/monthly-transactions/{userId}")
    public List<MonthlyTransactionDTO> getMonthlyTransactions(
            @PathVariable Long userId) {

        return dashboardService.getMonthlyTransactions(userId);

    }
    @GetMapping("/test")
public String test() {
    return "Dashboard Controller Working";
}

}