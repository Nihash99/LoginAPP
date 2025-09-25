package com.example.loginapp.service;

public class OrderResponse {
    private boolean success;
    private String message;
    private Long orderId;

    public OrderResponse(boolean success, String message, Long orderId) {
        this.success = success;
        this.message = message;
        this.orderId = orderId;
    }

    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public Long getOrderId() { return orderId; }
}
