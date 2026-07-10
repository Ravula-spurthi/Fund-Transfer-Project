package com.fundtransfer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fundtransfer.dto.ChangeTransactionPinDTO;
import com.fundtransfer.dto.SetTransactionPinDTO;
import com.fundtransfer.entity.User;
import com.fundtransfer.repository.UserRepository;

@Service
public class TransactionPinService {

    @Autowired
    private UserRepository repository;

    public String setPin(SetTransactionPinDTO dto) {

        User user = repository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (Boolean.TRUE.equals(user.getPinCreated())) {
            return "Transaction PIN already created";
        }

        user.setTransactionPin(dto.getNewPin());
        user.setPinCreated(true);

        repository.save(user);

        return "Transaction PIN Created Successfully";
    }

    public String changePin(ChangeTransactionPinDTO dto) {

        User user = repository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!dto.getOldPin().equals(user.getTransactionPin())) {
            return "Old PIN is incorrect";
        }

        user.setTransactionPin(dto.getNewPin());

        repository.save(user);

        return "Transaction PIN Updated Successfully";
    }

    public boolean verifyPin(Long userId, String pin) {

        User user = repository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return pin.equals(user.getTransactionPin());
    }
}