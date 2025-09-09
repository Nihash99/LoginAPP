package com.example.backend.controller;

import com.example.backend.dto.LoginDto;
import com.example.backend.dto.RequestOtpDto;
import com.example.backend.dto.VerifyOtpDto;
import com.example.backend.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {
    private final AuthService authService;
    public AuthController(AuthService authService) { this.authService = authService; }

    @PostMapping("/request-otp")
    public ResponseEntity<?> requestOtp(@Valid @RequestBody RequestOtpDto dto) {
        authService.requestOtp(dto.getEmail());
        return ResponseEntity.ok().body("{\"message\":\"OTP sent (or logged)\"}");
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@Valid @RequestBody VerifyOtpDto dto) {
        try {
            authService.verifyOtpAndSetPassword(dto);
            return ResponseEntity.ok().body("{\"message\":\"Verified and password set\"}");
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDto dto) {
        boolean ok = authService.login(dto);
        if (ok) return ResponseEntity.ok().body("{\"message\":\"Login success\"}");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"message\":\"Invalid credentials or unverified\"}");
    }
}
