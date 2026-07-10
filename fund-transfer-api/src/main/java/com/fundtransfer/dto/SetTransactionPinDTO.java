package com.fundtransfer.dto;

public class SetTransactionPinDTO {

    private Long userId;
    private String newPin;

    public SetTransactionPinDTO() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getNewPin() {
        return newPin;
    }

    public void setNewPin(String newPin) {
        this.newPin = newPin;
    }
}