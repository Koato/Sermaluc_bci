package com.bci.sermaluc.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class LoginRequestDTO implements Serializable {

    private String email;
    private String password;
}
