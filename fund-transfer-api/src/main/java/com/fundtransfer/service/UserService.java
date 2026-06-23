package com.fundtransfer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fundtransfer.entity.User;
import com.fundtransfer.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User updateUser(Long id, User user) {

        User existing = userRepository.findById(id).orElse(null);

        if (existing != null) {
            existing.setName(user.getName());
            existing.setEmail(user.getEmail());
            existing.setMobile(user.getMobile());
            existing.setAccountNumber(user.getAccountNumber());
            existing.setPassword(user.getPassword());
            existing.setBalance(user.getBalance());

            return userRepository.save(existing);
        }

        return null;
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
    public User registerUser(User user) {
        return userRepository.save(user);
    }
}