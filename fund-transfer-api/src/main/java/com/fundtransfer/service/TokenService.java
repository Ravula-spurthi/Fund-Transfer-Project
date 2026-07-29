package com.fundtransfer.service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.springframework.stereotype.Service;

@Service
public class TokenService {

    private static final String BEARER_PREFIX = "Bearer ";

    public String generateToken(Long userId, String email) {
        String payload = userId + ":" + email + ":" + System.currentTimeMillis();
        String encodedPayload = Base64.getEncoder().encodeToString(payload.getBytes(StandardCharsets.UTF_8));
        return BEARER_PREFIX + encodedPayload;
    }

    public boolean isValidToken(String token) {
        if (token == null || token.isBlank()) {
            return false;
        }

        String trimmedToken = token.trim();
        if (!trimmedToken.startsWith(BEARER_PREFIX)) {
            return false;
        }

        String payload = trimmedToken.substring(BEARER_PREFIX.length());
        return !payload.isBlank();
    }

    public Long extractUserId(String token) {
        String payload = decodeToken(token);
        if (payload == null || payload.isBlank()) {
            return null;
        }

        String[] parts = payload.split(":");
        if (parts.length < 1) {
            return null;
        }

        return Long.parseLong(parts[0]);
    }

    public String extractEmail(String token) {
        String payload = decodeToken(token);
        if (payload == null || payload.isBlank()) {
            return null;
        }

        String[] parts = payload.split(":", 3);
        if (parts.length < 2) {
            return null;
        }

        return parts[1];
    }

    private String decodeToken(String token) {
        if (!isValidToken(token)) {
            return null;
        }

        String encodedPayload = token.trim().substring(BEARER_PREFIX.length());
        try {
            byte[] decodedBytes = Base64.getDecoder().decode(encodedPayload);
            return new String(decodedBytes, StandardCharsets.UTF_8);
        } catch (IllegalArgumentException exception) {
            return null;
        }
    }
}
