package com.fundtransfer.service;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
import com.fundtransfer.entity.User;
import com.fundtransfer.repository.UserRepository;
 
@Service
public class AuthService {
 
    @Autowired
    private UserRepository userRepository;
 
    public String login(String email, String password) {
 
        User user = userRepository.findByEmail(email);
 
        if (user == null) {
            return "User Not Found";
        }
 
        if (user.getPassword().equals(password)) {
            return "Login Successful";
        }
 
        return "Invalid Password";
    }
}
 