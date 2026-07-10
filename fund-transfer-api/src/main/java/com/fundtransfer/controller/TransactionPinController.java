package com.fundtransfer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fundtransfer.dto.ChangeTransactionPinDTO;
import com.fundtransfer.dto.SetTransactionPinDTO;
import com.fundtransfer.service.TransactionPinService;

@RestController
@RequestMapping("/transaction-pin")
@CrossOrigin("*")
public class TransactionPinController {

    @Autowired
    private TransactionPinService service;

    @PostMapping("/set")
    public String setPin(@RequestBody SetTransactionPinDTO dto) {
        return service.setPin(dto);
    }

    @PostMapping("/change")
    public String changePin(@RequestBody ChangeTransactionPinDTO dto) {
        return service.changePin(dto);
    }

    @PostMapping("/verify")
    public boolean verifyPin(@RequestParam Long userId,
                             @RequestParam String pin) {
        return service.verifyPin(userId, pin);
    }
}