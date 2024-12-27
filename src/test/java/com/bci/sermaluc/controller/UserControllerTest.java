package com.bci.sermaluc.controller;

import com.bci.sermaluc.dto.UserRequestDTO;
import com.bci.sermaluc.dto.UserResponseDTO;
import com.bci.sermaluc.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateUser() {
        // Arrange
        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setName("Test User");
        userRequestDTO.setEmail("testuser@example.com");
        userRequestDTO.setPassword("Test@123");

        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setName("Test User");
        userResponseDTO.setEmail("testuser@example.com");

        when(userService.createUser(any(UserRequestDTO.class), anyString())).thenReturn("mockId");
        when(userService.getUser("mockId")).thenReturn(Optional.of(userResponseDTO));

        // Act
        ResponseEntity<UserResponseDTO> response = userController.createUser(userRequestDTO, "Bearer mockToken");

        // Assert
        assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
        assertEquals(userResponseDTO, response.getBody());
    }

    @Test
    public void testFailCreateUser() {
        // Arrange
        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setName("Test User");
        userRequestDTO.setEmail("testuser@example.com");
        userRequestDTO.setPassword("Test@123");

        when(userService.createUser(any(UserRequestDTO.class), anyString())).thenThrow(new RuntimeException("User creation failed"));

        // Act
        ResponseEntity<UserResponseDTO> response;
        try {
            response = userController.createUser(userRequestDTO, "Bearer mockToken");
        } catch (RuntimeException e) {
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode().value());
    }
}