package com.bci.sermaluc;

import com.bci.sermaluc.controller.UserController;
import com.bci.sermaluc.dto.UserRequestDTO;
import com.bci.sermaluc.dto.UserResponseDTO;
import com.bci.sermaluc.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
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

        when(userService.createUser(any(UserRequestDTO.class), any(String.class))).thenReturn("mockId");
        when(userService.getUser("mockId")).thenReturn(Optional.of(userResponseDTO));

        // Act
        ResponseEntity<UserResponseDTO> response = userController.createUser(userRequestDTO, "Bearer mockToken");

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(userResponseDTO, response.getBody());
    }
}