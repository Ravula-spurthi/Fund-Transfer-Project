package com.fundtransfer.controller;
 
import java.util.Map;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fundtransfer.service.AuthService;
 
@RestController
public class LoginController {
 
    @Autowired
    private AuthService authService;
 
    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> request) {
 
        String email = request.get("email");
        String password = request.get("password");
 
        return authService.login(email, password);
    }
}
 