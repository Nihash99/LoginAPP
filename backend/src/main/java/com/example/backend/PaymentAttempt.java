package com.example.loginapp.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class PaymentAttempt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long orderId;
    private String requestId;
    private String status; // INITIATED, SUCCESS, FAILED, ERROR
    private String paymentProviderTransactionId;
    private String errorMessage;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // getters and setters
    // ...
}
