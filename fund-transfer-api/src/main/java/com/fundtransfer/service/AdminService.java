package com.fundtransfer.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fundtransfer.entity.Admin;
import com.fundtransfer.entity.User;
import com.fundtransfer.repository.AdminRepository;
import com.fundtransfer.repository.UserRepository;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private UserRepository userRepository;


    public Optional<Admin> login(String email,
                                 String password) {

        Optional<Admin> admin =
                adminRepository.findByEmail(email);

        if (admin.isPresent()
                && admin.get().getPassword().equals(password)) {

            return admin;
        }

        return Optional.empty();
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}