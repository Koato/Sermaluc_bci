package com.bci.sermaluc.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Map;
import java.util.Objects;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    public void setUp() {
        globalExceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    public void handleEmailAlreadyRegisteredShouldReturnConflictStatus() {
        EmailAlreadyRegisteredException exception = new EmailAlreadyRegisteredException("Email already registered");

        ResponseEntity<Map<String, String>> response = globalExceptionHandler.handleEmailAlreadyRegistered(exception);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Email already registered", Objects.requireNonNull(response.getBody()).get("mensaje"));
    }

    @Test
    public void handleGenericExceptionShouldReturnInternalServerErrorStatus() {
        Exception exception = new Exception("Unexpected error");

        ResponseEntity<Map<String, String>> response = globalExceptionHandler.handleGenericException(exception);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Ocurrió un error inesperado", Objects.requireNonNull(response.getBody()).get("mensaje"));
    }
}