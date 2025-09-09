package com.example.backend.dto;

import java.time.Instant;

public class UserResponse {
    private Long id; private String email; private boolean verified; private Instant createdAt;
    public UserResponse(Long id, String email, boolean verified, Instant createdAt) {
        this.id = id; this.email = email; this.verified = verified; this.createdAt = createdAt;
    }
    public Long getId() { return id; }
    public String getEmail() { return email; }
    public boolean isVerified() { return verified; }
    public Instant getCreatedAt() { return createdAt; }
}
