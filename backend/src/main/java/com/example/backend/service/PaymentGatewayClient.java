package com.example.loginapp.service;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Random;

@Component
public class PaymentGatewayClient {

    // Simulate a payment API (success or failure)
    public PaymentResponse charge(String paymentInfo, BigDecimal amount) throws ExternalServiceException {
        // Randomly fail for testing purposes
        boolean success = new Random().nextBoolean();

        if (!success) {
            return new PaymentResponse(false, "Card declined", null);
        }

        String transactionId = "TXN" + System.currentTimeMillis();
        return new PaymentResponse(true, null, transactionId);
    }
}
