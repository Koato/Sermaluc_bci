package com.bci.sermaluc.service.impl;

import com.bci.sermaluc.dto.UserRequestDTO;
import com.bci.sermaluc.dto.UserResponseDTO;
import com.bci.sermaluc.entity.UserEntity;
import com.bci.sermaluc.exception.EmailAlreadyRegisteredException;
import com.bci.sermaluc.mapper.UserMapper;
import com.bci.sermaluc.repository.UserRepository;
import com.bci.sermaluc.service.PasswordValidatorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Optional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordValidatorService passwordValidatorService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createUserShouldReturnUserIdWhenUserIsCreatedSuccessfully() {
        UserRequestDTO userRequest = new UserRequestDTO();
        userRequest.setEmail("test@example.com");
        userRequest.setPassword("ValidPassword123!");

        UserEntity userEntity = new UserEntity();
        userEntity.setId(UUID.randomUUID());
        userEntity.setEmail("test@example.com");
        userEntity.setPassword("ValidPassword123!");

        when(passwordValidatorService.isValidPassword(userRequest.getPassword())).thenReturn(true);
        when(userRepository.existsByEmail(userRequest.getEmail())).thenReturn(false);
        when(userMapper.toEntity(userRequest)).thenReturn(userEntity);
        when(userRepository.save(userEntity)).thenReturn(userEntity);

        String userId = userService.createUser(userRequest, "token");

        assertEquals(userEntity.getId().toString(), userId);
    }

    @Test
    public void createUserShouldThrowExceptionWhenEmailIsAlreadyRegistered() {
        UserRequestDTO userRequest = new UserRequestDTO();
        userRequest.setEmail("test@example.com");
        userRequest.setPassword("ValidPassword123!");

        when(passwordValidatorService.isValidPassword(userRequest.getPassword())).thenReturn(true);
        when(userRepository.existsByEmail(userRequest.getEmail())).thenReturn(true);

        assertThrows(EmailAlreadyRegisteredException.class, () -> userService.createUser(userRequest, "token"));
    }

    @Test
    public void createUserShouldThrowExceptionWhenPasswordIsInvalid() {
        UserRequestDTO userRequest = new UserRequestDTO();
        userRequest.setEmail("test@example.com");
        userRequest.setPassword("invalid");

        when(passwordValidatorService.isValidPassword(userRequest.getPassword())).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> userService.createUser(userRequest, "token"));
    }

    @Test
    public void getUserShouldReturnUserResponseWhenUserExists() {
        UUID userId = UUID.randomUUID();
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userId);
        userEntity.setEmail("test@example.com");
        userEntity.setPassword("ValidPassword123!");

        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));

        Optional<UserResponseDTO> userResponse = userService.getUser(userId.toString());

        assertTrue(userResponse.isPresent());
        assertEquals(userEntity.getId(), userResponse.get().getId());
        assertEquals(userEntity.getEmail(), userResponse.get().getEmail());
    }

    @Test
    public void getUserShouldReturnEmptyWhenUserDoesNotExist() {
        UUID userId = UUID.randomUUID();

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        Optional<UserResponseDTO> userResponse = userService.getUser(userId.toString());

        assertFalse(userResponse.isPresent());
    }

    @Test
    public void lastLoginShouldUpdateLastLoginWhenUserExists() {
        String email = "test@example.com";
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(email);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(userEntity));

        userService.lastLogin(email);

        assertNotNull(userEntity.getLastLogin());
        verify(userRepository).save(userEntity);
    }

    @Test
    public void lastLoginShouldDoNothingWhenUserDoesNotExist() {
        String email = "nonexistent@example.com";

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        userService.lastLogin(email);

        verify(userRepository, never()).save(any(UserEntity.class));
    }
}