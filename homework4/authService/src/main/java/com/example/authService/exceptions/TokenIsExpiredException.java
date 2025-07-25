package com.example.authService.exceptions;

public class TokenIsExpiredException extends RuntimeException {
    public TokenIsExpiredException(String message) {
        super(message);
    }
}
