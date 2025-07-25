package com.example.authService.exceptions;

public class PasswordNotMatchesException extends RuntimeException{
    public PasswordNotMatchesException(String message) {
        super(message);
    }
}
