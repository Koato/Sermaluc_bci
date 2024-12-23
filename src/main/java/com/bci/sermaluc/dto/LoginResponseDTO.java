package com.bci.sermaluc.dto;

import lombok.*;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class LoginResponseDTO implements Serializable {

    private String token;
}
