package org.example.springdemo4.service;

import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.springdemo4.dto.request.LoginRequest;
import org.example.springdemo4.dto.request.RefreshTokenRequest;
import org.example.springdemo4.dto.response.AccessTokenResponse;
import org.example.springdemo4.dto.response.AuthResponse;
import org.example.springdemo4.exception.InvalidTokenException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final MyUserDetailsService userDetailsService;
    private final JwtService jwtService;

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.username());
        log.info("User logged in: {}", userDetails.getUsername());

        return new AuthResponse(
                jwtService.generateAccessToken(userDetails),
                jwtService.generateRefreshToken(userDetails)
        );
    }

    public AccessTokenResponse refresh(RefreshTokenRequest request) {
        String refreshToken = request.refreshToken();

        try {
            String username = jwtService.extractUsername(refreshToken);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if (!jwtService.isRefreshTokenValid(refreshToken, userDetails)) {
                throw new InvalidTokenException("Refresh token is invalid");
            }

            log.info("Access token refreshed for user: {}", username);
            return new AccessTokenResponse(jwtService.generateAccessToken(userDetails));
        } catch (JwtException | IllegalArgumentException | UsernameNotFoundException e) {
            log.error("Refresh failed: {}", e.getMessage());
            throw new InvalidTokenException("Refresh token is invalid or expired");
        }
    }
}