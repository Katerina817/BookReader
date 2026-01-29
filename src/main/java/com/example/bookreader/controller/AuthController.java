package com.example.bookreader.controller;

import com.example.bookreader.DTO.AuthControllerDTO.AuthResponse;
import com.example.bookreader.DTO.AuthControllerDTO.LoginRequest;
import com.example.bookreader.DTO.AuthControllerDTO.RegisterRequest;
import com.example.bookreader.entity.User;
import com.example.bookreader.mapper.AuthMapper;
import com.example.bookreader.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public AuthResponse register(@RequestBody @Valid RegisterRequest registerRequest) {
        User user = authService.register(registerRequest);
        return AuthMapper.toResponse(user);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody @Valid LoginRequest loginRequest) {
        User user = authService.login(loginRequest);
        return AuthMapper.toResponse(user);
    }
}
