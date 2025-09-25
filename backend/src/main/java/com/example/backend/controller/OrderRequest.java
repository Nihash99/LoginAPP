package com.example.loginapp.controller;

import java.math.BigDecimal;

public class OrderRequest {
    private String requestId;
    private BigDecimal amount;
    private String paymentInfo;

    // getters and setters
    public String getRequestId() { return requestId; }
    public void setRequestId(String requestId) { this.requestId = requestId; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public String getPaymentInfo() { return paymentInfo; }
    public void setPaymentInfo(String paymentInfo) { this.paymentInfo = paymentInfo; }
}
