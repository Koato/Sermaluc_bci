package com.bci.sermaluc.service;

import com.bci.sermaluc.dto.LoginRequestDTO;

public interface AuthService {

    String login(LoginRequestDTO loginDto);
}
