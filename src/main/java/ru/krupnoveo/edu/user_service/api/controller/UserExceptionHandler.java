package ru.krupnoveo.edu.user_service.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.krupnoveo.edu.user_service.api.dto.response.ApiErrorResponse;
import ru.krupnoveo.edu.user_service.api.exception.UserNotFoundException;

import java.security.SignatureException;

@ControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ApiErrorResponse> handleUserNotFoundException(UserNotFoundException ex) {
        return new ResponseEntity<>(
                new ApiErrorResponse(
                        HttpStatus.NOT_FOUND.value(),
                        ex.getMessage()
                ),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(SignatureException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ApiErrorResponse> handleSignatureException(SignatureException ex) {
        return new ResponseEntity<>(
                new ApiErrorResponse(
                        HttpStatus.UNAUTHORIZED.value(),
                        ex.getMessage()
                ),
                HttpStatus.UNAUTHORIZED
        );
    }
}
