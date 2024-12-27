package com.bci.sermaluc.service;

import com.bci.sermaluc.dto.UserRequestDTO;
import com.bci.sermaluc.dto.UserResponseDTO;
import java.util.Optional;

public interface UserService {

    String createUser(UserRequestDTO userRequest, String token);
    Optional<UserResponseDTO> getUser(String uuid);
    void lastLogin(String email);
}
