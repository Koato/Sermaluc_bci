package com.bci.sermaluc.controller;

import com.bci.sermaluc.dto.LoginRequestDTO;
import com.bci.sermaluc.dto.LoginResponseDTO;
import com.bci.sermaluc.service.AuthService;
import com.bci.sermaluc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    // Build Login REST API
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginDto){

        String token = authService.login(loginDto);
        LoginResponseDTO authResponseDto = new LoginResponseDTO();
        authResponseDto.setToken(token);
        userService.lastLogin(loginDto.getEmail());
        return new ResponseEntity<>(authResponseDto, HttpStatus.OK);
    }
}
