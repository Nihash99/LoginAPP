package com.example.backend.controller;

import com.example.backend.dto.UserResponse;
import com.example.backend.repository.UserRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UsersController {
    private final UserRepository userRepository;
    public UsersController(UserRepository userRepository) { this.userRepository = userRepository; }

    @GetMapping
    public List<UserResponse> all() {
        return userRepository.findAll().stream()
                .map(u -> new UserResponse(u.getId(), u.getEmail(), u.isVerified(), u.getCreatedAt()))
                .toList();
    }
}
