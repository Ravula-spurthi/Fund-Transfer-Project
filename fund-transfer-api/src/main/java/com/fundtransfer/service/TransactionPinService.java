package com.fundtransfer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fundtransfer.dto.TransactionPinDTO;
import com.fundtransfer.entity.User;
import com.fundtransfer.repository.UserRepository;

@Service
public class TransactionPinService {

    @Autowired
    private UserRepository repository;

    public String setPin(TransactionPinDTO dto) {

        User user = repository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setTransactionPin(dto.getNewPin());
        user.setPinCreated(true);

        repository.save(user);

        return "Transaction PIN Created Successfully";
    }

    public String changePin(TransactionPinDTO dto) {

        User user = repository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getTransactionPin().equals(dto.getOldPin())) {
            throw new RuntimeException("Invalid Old PIN");
        }

        user.setTransactionPin(dto.getNewPin());

        repository.save(user);

        return "Transaction PIN Updated Successfully";
    }
}