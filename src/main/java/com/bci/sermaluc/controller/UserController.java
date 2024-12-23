package com.bci.sermaluc.controller;

import com.bci.sermaluc.dto.UserRequestDTO;
import com.bci.sermaluc.dto.UserResponseDTO;
import com.bci.sermaluc.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/create")
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserRequestDTO userRequest, @RequestHeader(value = "Authorization", required = true) String authorizationHeader) {
        String id = userService.createUser(userRequest, authorizationHeader);
        return userService.getUser(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
