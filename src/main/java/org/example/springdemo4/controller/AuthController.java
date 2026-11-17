package org.example.springdemo4.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.springdemo4.dto.request.LoginRequest;
import org.example.springdemo4.dto.request.RefreshTokenRequest;
import org.example.springdemo4.dto.response.AccessTokenResponse;
import org.example.springdemo4.dto.response.AuthResponse;
import org.example.springdemo4.service.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @PostMapping("/refresh")
    public AccessTokenResponse refresh(@Valid @RequestBody RefreshTokenRequest request) {
        return authService.refresh(request);
    }
}