package com.fundtransfer.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fundtransfer.entity.Admin;
import com.fundtransfer.repository.AdminRepository;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

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

}