package com.fundtransfer.entity;
 
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
 
@Entity
@Table(name = "transactions")
public class Transaction {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
 
    private String beneficiaryName;
    private Double amount;
    private String status;
    private LocalDate transactionDate;
 
    public Long getId() {
        return id;
    }
 
    public void setId(Long id) {
        this.id = id;
    }
 
    public String getBeneficiaryName() {
        return beneficiaryName;
    }
 
    public void setBeneficiaryName(String beneficiaryName) {
        this.beneficiaryName = beneficiaryName;
    }
 
    public Double getAmount() {
        return amount;
    }
 
    public void setAmount(Double amount) {
        this.amount = amount;
    }
 
    public String getStatus() {
        return status;
    }
 
    public void setStatus(String status) {
        this.status = status;
    }
 
    public LocalDate getTransactionDate() {
        return transactionDate;
    }
 
    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }
}
 