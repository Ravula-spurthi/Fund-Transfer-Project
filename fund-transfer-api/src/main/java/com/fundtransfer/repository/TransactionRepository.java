package com.fundtransfer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fundtransfer.entity.Transaction;

public interface TransactionRepository
        extends JpaRepository<Transaction, Long> {

}