 package com.fundtransfer.entity;
 
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
 
@Entity
@Table(name = "beneficiaries")
public class Beneficiary {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
 
    private String beneficiaryName;
    private String accountNumber;
    private String ifscCode;
 
    public Beneficiary() {
    }
 
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
 
    public String getAccountNumber() {
        return accountNumber;
    }
 
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
 
    public String getIfscCode() {
        return ifscCode;
    }
 
    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }
}
 