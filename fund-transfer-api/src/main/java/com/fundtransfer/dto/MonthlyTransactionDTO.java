package com.fundtransfer.dto;

public class MonthlyTransactionDTO {

    private String month;
    private Double totalAmount;

    public MonthlyTransactionDTO() {
    }

    public MonthlyTransactionDTO(String month, Double totalAmount) {
        this.month = month;
        this.totalAmount = totalAmount;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }
}