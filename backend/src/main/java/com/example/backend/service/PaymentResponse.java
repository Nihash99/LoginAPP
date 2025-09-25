package com.example.loginapp.service;

public class PaymentResponse {
    private boolean success;
    private String errorMessage;
    private String transactionId;

    public PaymentResponse(boolean success, String errorMessage, String transactionId) {
        this.success = success;
        this.errorMessage = errorMessage;
        this.transactionId = transactionId;
    }

    public boolean isSuccess() { return success; }
    public String getErrorMessage() { return errorMessage; }
    public String getTransactionId() { return transactionId; }
}
