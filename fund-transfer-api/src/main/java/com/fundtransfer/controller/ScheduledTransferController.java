package com.fundtransfer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    // Get All Scheduled Transfers
    @GetMapping
    public List<ScheduledTransfer> getAllTransfers() {

        return service.getAllTransfers();
    }

    // Get Only Pending Transfers
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
    public void deleteTransfer(@PathVariable Long id) {

        service.deleteTransfer(id);
    }

    // Execute Scheduled Transfer
    @PostMapping("/{id}/execute")
    public ScheduledTransfer executeTransfer(
            @PathVariable Long id) {

        return service.executeTransfer(id);
    }

}