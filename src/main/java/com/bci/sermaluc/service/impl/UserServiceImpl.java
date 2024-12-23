package com.bci.sermaluc.service.impl;

import com.bci.sermaluc.dto.UserRequestDTO;
import com.bci.sermaluc.dto.UserResponseDTO;
import com.bci.sermaluc.entity.UserEntity;
import com.bci.sermaluc.exception.EmailAlreadyRegisteredException;
import com.bci.sermaluc.mapper.UserMapper;
import com.bci.sermaluc.repository.UserRepository;
import com.bci.sermaluc.service.PasswordValidatorService;
import com.bci.sermaluc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordValidatorService passwordValidatorService;

    @Override
    public String createUser(UserRequestDTO userRequest, String token) {
        validatePassword(userRequest.getPassword());
        validateExistEmail(userRequest.getEmail());

        UserEntity user = userMapper.toEntity(userRequest);
        user.setToken(token);

        userRepository.save(user);
        return user.getId().toString();
    }

    @Override
    public Optional<UserResponseDTO> getUser(String id) {
        return userRepository.findById(UUID.fromString(id))
                .map(user -> new UserResponseDTO(
                        user.getId(),
                        user.getName(),
                        user.getEmail(),
                        user.getCreated(),
                        user.getModified(),
                        user.getLastLogin(),
                        user.getToken(),
                        user.isActive()
                ));
    }

    @Override
    public void lastLogin(String email) {
        userRepository.findByEmail(email)
                .ifPresent(user -> {
                    user.setLastLogin(LocalDateTime.now());
                    userRepository.save(user);
                });
    }

    public void validateExistEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new EmailAlreadyRegisteredException("El correo ya está registrado");
        }
    }

    public void validatePassword(String password) {
        if (!passwordValidatorService.isValidPassword(password)) {
            throw new IllegalArgumentException("La contraseña no cumple con los requisitos de seguridad");
        }
    }
}
