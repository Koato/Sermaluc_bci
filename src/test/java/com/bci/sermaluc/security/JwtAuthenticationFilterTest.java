package com.bci.sermaluc.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class JwtAuthenticationFilterTest {

    @InjectMocks
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Mock
    private Jwt jwtTokenProvider;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void doFilterInternalShouldAuthenticateUserWhenTokenIsValid() throws ServletException, IOException {
        String token = "validToken";
        String username = "testUser";
        UserDetails userDetails = mock(UserDetails.class);

        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(jwtTokenProvider.validateToken(token)).thenReturn(true);
        when(jwtTokenProvider.getUsername(token)).thenReturn(username);
        when(userDetailsService.loadUserByUsername(username)).thenReturn(userDetails);

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        assertNotNull(authentication);
        assertEquals(userDetails, authentication.getPrincipal());
        verify(filterChain).doFilter(request, response);
    }

    @Test
    public void doFilterInternalShouldNotAuthenticateUserWhenTokenIsInvalid() throws ServletException, IOException {
        String token = "invalidToken";

        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(jwtTokenProvider.validateToken(token)).thenReturn(false);

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain).doFilter(request, response);
    }

    @Test
    public void doFilterInternalShouldNotAuthenticateUserWhenTokenIsMissing() throws ServletException, IOException {
        when(request.getHeader("Authorization")).thenReturn(null);

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain).doFilter(request, response);
    }

    @Test
    public void getTokenFromRequestShouldReturnTokenWhenBearerTokenIsPresent() {
        String bearerToken = "Bearer validToken";

        when(request.getHeader("Authorization")).thenReturn(bearerToken);

        String token = jwtAuthenticationFilter.getTokenFromRequest(request);

        assertEquals("validToken", token);
    }

    @Test
    public void getTokenFromRequestShouldReturnNullWhenBearerTokenIsNotPresent() {
        when(request.getHeader("Authorization")).thenReturn(null);

        String token = jwtAuthenticationFilter.getTokenFromRequest(request);

        assertNull(token);
    }

    @Test
    public void getTokenFromRequestShouldReturnNullWhenBearerTokenIsInvalid() {
        String bearerToken = "InvalidToken";

        when(request.getHeader("Authorization")).thenReturn(bearerToken);

        String token = jwtAuthenticationFilter.getTokenFromRequest(request);

        assertNull(token);
    }
}