package com.fundtransfer.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fundtransfer.dto.FundTransferDTO;
import com.fundtransfer.entity.User;
import com.fundtransfer.repository.UserRepository;

@Service
public class FundTransferService {

    @Autowired
    private UserRepository userRepository;

    public String transferMoney(FundTransferDTO request) {

        Optional<User> senderOptional =
                userRepository.findByAccountNumber(request.getSenderAccount());

        Optional<User> receiverOptional =
                userRepository.findByAccountNumber(request.getReceiverAccount());

        if (senderOptional.isEmpty()) {
            return "Sender Account Not Found";
        }

        if (receiverOptional.isEmpty()) {
            return "Receiver Account Not Found";
        }

        User sender = senderOptional.get();
        User receiver = receiverOptional.get();

        if (request.getAmount() <= 0) {
            return "Invalid Amount";
        }

        if (sender.getBalance() < request.getAmount()) {
            return "Insufficient Balance";
        }

        sender.setBalance(sender.getBalance() - request.getAmount());
        receiver.setBalance(receiver.getBalance() + request.getAmount());

        userRepository.save(sender);
        userRepository.save(receiver);

        return "Fund Transfer Successful";
    }
}