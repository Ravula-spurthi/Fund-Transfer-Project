package com.fundtransfer.dto;

public class DashboardDTO {

    private String name;
    private String accountNumber;
    private Double balance;
    private String lastTransaction;

    public DashboardDTO() {
    }

    public DashboardDTO(String name, String accountNumber, Double balance, String lastTransaction) {
        this.name = name;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.lastTransaction = lastTransaction;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getLastTransaction() {
        return lastTransaction;
    }

    public void setLastTransaction(String lastTransaction) {
        this.lastTransaction = lastTransaction;
    }
}