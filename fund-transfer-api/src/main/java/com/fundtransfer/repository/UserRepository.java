package com.fundtransfer.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fundtransfer.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    Optional<User> findByAccountNumber(String accountNumber);

}