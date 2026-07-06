package com.fundtransfer.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fundtransfer.dto.ForgotPasswordDTO;
import com.fundtransfer.dto.LoginResponseDTO;
import com.fundtransfer.dto.RegisterDTO;
import com.fundtransfer.entity.User;
import com.fundtransfer.repository.UserRepository;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    public Object login(String email, String password) {
       // System.out.println(email);
       // System.out.println(password);

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

    Optional<User> optionalUser =
            userRepository.findByEmailAndMobile(
                    forgotPasswordDTO.getEmail(),
                    forgotPasswordDTO.getMobile());

    if (optionalUser.isEmpty()) {
        return "Invalid Email or Mobile Number";
    }

    User user = optionalUser.get();

    user.setPassword(forgotPasswordDTO.getNewPassword());

    userRepository.save(user);

    return "Password Updated Successfully";
}

 public String register(RegisterDTO registerDTO) {

    if (userRepository.findByEmail(registerDTO.getEmail()).isPresent()) {
        return "Email already exists";
    }

    if (userRepository.findByAccountNumber(registerDTO.getAccountNumber()).isPresent()) {
        return "Account Number already exists";
    }

    if (userRepository.findByMobile(registerDTO.getMobile()).isPresent()) {
        return "Mobile Number already exists";
    }

    User user = new User();

    user.setName(registerDTO.getName());
    user.setEmail(registerDTO.getEmail());
    user.setMobile(registerDTO.getMobile());
    user.setAccountNumber(registerDTO.getAccountNumber());
    user.setPassword(registerDTO.getPassword());
    user.setBalance(registerDTO.getBalance());

    userRepository.save(user);

    return "User Registered Successfully";
}
}