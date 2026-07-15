package com.fundtransfer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fundtransfer.dto.FundTransferDTO;
import com.fundtransfer.service.FundTransferService;

@RestController
@RequestMapping("/api/fund-transfer")
@CrossOrigin(origins = "*")
public class FundTransferController {

    @Autowired
    private FundTransferService fundTransferService;

    @PostMapping
    public ResponseEntity<?> transferMoney(@RequestBody FundTransferDTO request) {

        try {
            String message = fundTransferService.transferFunds(request);
            return ResponseEntity.ok(message);

        } catch (RuntimeException e) {

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());

        }

    }
}