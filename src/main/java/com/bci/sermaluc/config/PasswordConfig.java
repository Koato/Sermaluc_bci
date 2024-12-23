package com.bci.sermaluc.config;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Component
@ConfigurationProperties(prefix = "password")
public class PasswordConfig {

    private String regex;
}
