package com.fundtransfer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fundtransfer.dto.ForgotPasswordDTO;
import com.fundtransfer.dto.LoginDTO;
import com.fundtransfer.dto.RegisterDTO;
import com.fundtransfer.service.AuthService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class LoginController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public Object login(@RequestBody LoginDTO loginDTO) {
        return authService.login(loginDTO.getEmail(), loginDTO.getPassword());
    }

    @PostMapping("/forgot-password")
    public String forgotPassword(@RequestBody ForgotPasswordDTO forgotPasswordDTO) {
        return authService.forgotPassword(forgotPasswordDTO);
    }

    @PostMapping("/register")
    public String register(@RequestBody RegisterDTO registerDTO) {
    return authService.register(registerDTO);
    }
}