package com.bci.sermaluc.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class JwtTest {

    @InjectMocks
    private Jwt jwt;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void generateTokenShouldReturnValidToken() {
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("testUser");

        String token = jwt.generateToken(authentication);

        assertNotNull(token);
        assertTrue(jwt.validateToken(token));
    }

    @Test
    public void getUsernameShouldReturnCorrectUsername() {
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("testUser");

        String token = jwt.generateToken(authentication);
        String username = jwt.getUsername(token);

        assertEquals("testUser", username);
    }

    @Test
    public void validateTokenShouldReturnFalseForExpiredToken() {
        String expiredToken = Jwts.builder()
                .subject("testUser")
                .issuedAt(new Date(System.currentTimeMillis() - 3600000))
                .expiration(new Date(System.currentTimeMillis() - 1800000))
                .signWith(jwt.key(), SignatureAlgorithm.HS256)
                .compact();

        assertFalse(jwt.validateToken(expiredToken));
    }

    @Test
    public void validateTokenShouldThrowExceptionForInvalidToken() {
        String invalidToken = "invalidToken";

        assertThrows(io.jsonwebtoken.JwtException.class, () -> {
            jwt.validateToken(invalidToken);
        });
    }
}