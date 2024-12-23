package com.bci.sermaluc.service.impl;

import com.bci.sermaluc.config.PasswordConfig;
import com.bci.sermaluc.service.PasswordValidatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.regex.Pattern;

@Service
public class PasswordValidatorImpl implements PasswordValidatorService {

    @Autowired
    private PasswordConfig passwordConfig;

    @Override
    public boolean isValidPassword(String password) {
        String regex = passwordConfig.getRegex();
        return Pattern.matches(regex, password);
    }
}
