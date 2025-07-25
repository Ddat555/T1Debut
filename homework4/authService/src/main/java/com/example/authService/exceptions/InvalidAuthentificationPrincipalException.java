package com.example.authService.exceptions;

public class InvalidAuthentificationPrincipalException extends RuntimeException{
    public InvalidAuthentificationPrincipalException(String message) {
        super(message);
    }
}
