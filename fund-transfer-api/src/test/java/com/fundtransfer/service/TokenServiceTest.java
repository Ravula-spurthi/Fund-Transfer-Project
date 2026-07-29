package com.fundtransfer.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class TokenServiceTest {

    @Test
    void shouldGenerateAndValidateToken() {
        TokenService tokenService = new TokenService();

        String token = tokenService.generateToken(42L, "john@example.com");

        assertNotNull(token);
        assertTrue(token.startsWith("Bearer "));
        assertTrue(tokenService.isValidToken(token));
        assertEquals(42L, tokenService.extractUserId(token));
        assertEquals("john@example.com", tokenService.extractEmail(token));
    }
}
