package com.example.backend.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(nullable = false, unique = true) private String email;
    @Column(name = "password_hash") private String passwordHash;
    @Column(nullable = false) private boolean verified = false;
    private String otpCode;
    private Instant otpExpiry;
    @Column(nullable = false, updatable = false) private Instant createdAt = Instant.now();
    public User() {}
    public User(String email) { this.email = email; }
    public Long getId() { return id; } public void setId(Long id) { this.id = id; }
    public String getEmail() { return email; } public void setEmail(String email) { this.email = email; }
    public String getPasswordHash() { return passwordHash; } public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
    public boolean isVerified() { return verified; } public void setVerified(boolean verified) { this.verified = verified; }
    public String getOtpCode() { return otpCode; } public void setOtpCode(String otpCode) { this.otpCode = otpCode; }
    public Instant getOtpExpiry() { return otpExpiry; } public void setOtpExpiry(Instant otpExpiry) { this.otpExpiry = otpExpiry; }
    public Instant getCreatedAt() { return createdAt; } public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}
