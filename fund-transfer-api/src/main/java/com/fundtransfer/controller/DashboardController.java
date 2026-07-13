package com.fundtransfer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.fundtransfer.dto.DashboardDTO;
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

}