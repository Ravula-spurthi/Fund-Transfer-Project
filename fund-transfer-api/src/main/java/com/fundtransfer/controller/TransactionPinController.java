package com.fundtransfer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.fundtransfer.dto.TransactionPinDTO;
import com.fundtransfer.service.TransactionPinService;

@RestController
@RequestMapping("/transaction-pin")
@CrossOrigin("*")
public class TransactionPinController {

    @Autowired
    private TransactionPinService service;

    @PostMapping("/set")
    public String setPin(@RequestBody TransactionPinDTO dto) {
        return service.setPin(dto);
    }

    @PostMapping("/change")
    public String changePin(@RequestBody TransactionPinDTO dto) {
        return service.changePin(dto);
    }
}