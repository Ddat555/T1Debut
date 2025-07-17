package com.example.CommandService.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ErrorMaxThreadPoolExecutor.class)
    public ResponseEntity<?> errorMaxThreadPoolExecutor(ErrorMaxThreadPoolExecutor errorMaxThreadPoolExecutor){
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Пул потоков переполнен");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> methodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(methodArgumentNotValidException.getMessage());
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<?> nullPointerException(NullPointerException nullPointerException){
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Ошибка на сервере");
    }
}
