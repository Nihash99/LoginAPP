package com.example.backend.service;

import com.example.backend.dto.LoginDto;
import com.example.backend.dto.VerifyOtpDto;
import com.example.backend.entity.User;
import com.example.backend.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.security.SecureRandom;
import java.time.Instant;
import java.time.Duration;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final MailService mailService;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    private final SecureRandom random = new SecureRandom();

    public AuthService(UserRepository userRepository, MailService mailService) {
        this.userRepository = userRepository;
        this.mailService = mailService;
    }

    @Transactional
    public void requestOtp(String email) {
        String otp = String.format("%06d", random.nextInt(1_000_000));
        Instant expiry = Instant.now().plus(Duration.ofMinutes(10));
        User user = userRepository.findByEmail(email).orElseGet(() -> new User(email));
        user.setOtpCode(otp);
        user.setOtpExpiry(expiry);
        userRepository.save(user);
        mailService.sendOtp(email, otp);
    }

    @Transactional
    public void verifyOtpAndSetPassword(VerifyOtpDto dto) {
        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("User/OTP not found"));
        if (user.getOtpCode() == null || user.getOtpExpiry() == null) throw new IllegalArgumentException("OTP not requested");
        if (Instant.now().isAfter(user.getOtpExpiry())) throw new IllegalArgumentException("OTP expired");
        if (!dto.getOtp().equals(user.getOtpCode())) throw new IllegalArgumentException("Invalid OTP");
        user.setPasswordHash(encoder.encode(dto.getPassword()));
        user.setVerified(true);
        user.setOtpCode(null);
        user.setOtpExpiry(null);
        userRepository.save(user);
    }

    public boolean login(LoginDto dto) {
        return userRepository.findByEmail(dto.getEmail())
                .filter(User::isVerified)
                .filter(u -> u.getPasswordHash() != null && encoder.matches(dto.getPassword(), u.getPasswordHash()))
                .isPresent();
    }
}
