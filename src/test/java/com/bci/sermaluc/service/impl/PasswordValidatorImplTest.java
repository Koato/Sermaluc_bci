package com.bci.sermaluc.service.impl;

import com.bci.sermaluc.config.PasswordConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class PasswordValidatorImplTest {

    @InjectMocks
    private PasswordValidatorImpl passwordValidator;

    @Mock
    private PasswordConfig passwordConfig;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void isValidPasswordShouldReturnTrueWhenPasswordMatchesRegex() {
        String validPassword = "ValidPassword123!";
        when(passwordConfig.getRegex()).thenReturn("^(?=.*[0-9])(?=.*[a-zA-Z]).{8,}$");

        boolean result = passwordValidator.isValidPassword(validPassword);

        assertTrue(result);
    }

    @Test
    public void isValidPasswordShouldReturnFalseWhenPasswordDoesNotMatchRegex() {
        String invalidPassword = "short";
        when(passwordConfig.getRegex()).thenReturn("^(?=.*[0-9])(?=.*[a-zA-Z]).{8,}$");

        boolean result = passwordValidator.isValidPassword(invalidPassword);

        assertFalse(result);
    }

    @Test
    public void isValidPasswordShouldReturnFalseWhenPasswordIsEmpty() {
        when(passwordConfig.getRegex()).thenReturn("^(?=.*[0-9])(?=.*[a-zA-Z]).{8,}$");

        boolean result = passwordValidator.isValidPassword("");

        assertFalse(result);
    }
}