package org.example.springdemo4.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;

@Service
public class JwtService {

    private static final String TYPE_CLAIM = "type";
    private static final String ACCESS = "access";
    private static final String REFRESH = "refresh";

    private final SecretKey key;
    private final long accessExpirationMinutes;
    private final long refreshExpirationDays;

    public JwtService() {
        String secret = "CHANGE_ME_TO_A_LONG_RANDOM_SECRET_32CHARS_MIN";
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.accessExpirationMinutes = 15;
        this.refreshExpirationDays = 7;
    }

    public String generateAccessToken(UserDetails userDetails) {
        return buildToken(userDetails, ACCESS, Instant.now().plusSeconds(accessExpirationMinutes * 60));
    }

    public String generateRefreshToken(UserDetails userDetails) {
        return buildToken(userDetails, REFRESH, Instant.now().plusSeconds(refreshExpirationDays * 24 * 60 * 60));
    }

    private String buildToken(UserDetails userDetails, String type, Instant exp) {
        Instant now = Instant.now();

        return Jwts.builder()
                .subject(userDetails.getUsername())
                .claim(TYPE_CLAIM, type)
                .issuedAt(Date.from(now))
                .expiration(Date.from(exp))
                .signWith(key)
                .compact();
    }

    public String extractUsername(String token) {
        return parseAllClaims(token).getSubject();
    }

    public boolean isAccessTokenValid(String token, UserDetails userDetails) {
        return isTokenValid(token, userDetails, ACCESS);
    }

    public boolean isRefreshTokenValid(String token, UserDetails userDetails) {
        return isTokenValid(token, userDetails, REFRESH);
    }

    private boolean isTokenValid(String token, UserDetails userDetails, String expectedType) {
        Claims claims = parseAllClaims(token);
        String type = claims.get(TYPE_CLAIM, String.class);
        return claims.getSubject().equals(userDetails.getUsername())
                && expectedType.equals(type)
                && !isExpired(claims);
    }

    private boolean isExpired(Claims claims) {
        return claims.getExpiration().before(new Date());
    }

    private Claims parseAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}