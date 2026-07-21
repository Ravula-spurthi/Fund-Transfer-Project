package com.fundtransfer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fundtransfer.dto.ScheduledTransferDTO;
import com.fundtransfer.entity.ScheduledTransfer;
import com.fundtransfer.service.ScheduledTransferService;

@RestController
@RequestMapping("/scheduled-transfer")
@CrossOrigin(origins = "*")
public class ScheduledTransferController {

    @Autowired
    private ScheduledTransferService service;

    // Save Scheduled Transfer
    @PostMapping
    public ScheduledTransfer saveTransfer(
            @RequestBody ScheduledTransferDTO dto) {

        return service.saveTransfer(dto);
    }

    // Get Scheduled Transfers of Logged-in User
    @GetMapping("/user/{userId}")
    public List<ScheduledTransfer> getTransfersByUser(
            @PathVariable Long userId) {

        return service.getTransfersByUser(userId);
    }

    // Get All Scheduled Transfers (Optional - for Admin)
    @GetMapping
    public List<ScheduledTransfer> getAllTransfers() {

        return service.getAllTransfers();
    }

    // Get Pending Scheduled Transfers
    @GetMapping("/pending")
    public List<ScheduledTransfer> getPendingTransfers() {

        return service.getPendingTransfers();
    }

    // Update Scheduled Transfer
    @PutMapping("/{id}")
    public ScheduledTransfer updateTransfer(
            @PathVariable Long id,
            @RequestBody ScheduledTransferDTO dto) {

        return service.updateTransfer(id, dto);
    }

    // Delete Scheduled Transfer
    @DeleteMapping("/{id}")
    public void deleteTransfer(
            @PathVariable Long id) {

        service.deleteTransfer(id);
    }

    // Execute Scheduled Transfer
    @PostMapping("/{id}/execute")
    public ScheduledTransfer executeTransfer(
            @PathVariable Long id) {

        return service.executeTransfer(id);
    }

}