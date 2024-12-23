package com.bci.sermaluc;

import com.bci.sermaluc.controller.AuthController;
import com.bci.sermaluc.dto.LoginRequestDTO;
import com.bci.sermaluc.dto.LoginResponseDTO;
import com.bci.sermaluc.service.AuthService;
import com.bci.sermaluc.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class AuthControllerTest {

    @InjectMocks
    private AuthController authController;

    @Mock
    private UserService userService;

    @Mock
    private AuthService authService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLogin() {
        // Arrange
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO();
        loginRequestDTO.setEmail("admin@sermaluc.pe");
        loginRequestDTO.setPassword("Admin@123");

        String token = "mockToken";
        when(authService.login(any(LoginRequestDTO.class))).thenReturn(token);

        // Act
        ResponseEntity<LoginResponseDTO> response = authController.login(loginRequestDTO);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(token, response.getBody().getToken());
    }
}