package com.fundtransfer.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fundtransfer.dto.ForgotPasswordDTO;
import com.fundtransfer.dto.LoginResponseDTO;
import com.fundtransfer.entity.User;
import com.fundtransfer.repository.UserRepository;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    public Object login(String email, String password) {

        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isEmpty()) {
            return "User Not Found";
        }

        User user = optionalUser.get();

        if (!user.getPassword().equals(password)) {
            return "Invalid Password";
        }

        return new LoginResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getAccountNumber(),
                user.getBalance()
        );
    }

    public String forgotPassword(ForgotPasswordDTO forgotPasswordDTO) {

    Optional<User> optionalUser = userRepository.findByEmail(forgotPasswordDTO.getEmail());

    if (optionalUser.isEmpty()) {
        return "User Not Found";
    }

    User user = optionalUser.get();
    user.setPassword(forgotPasswordDTO.getNewPassword());

    userRepository.save(user);

    return "Password Updated Successfully";
 }
}