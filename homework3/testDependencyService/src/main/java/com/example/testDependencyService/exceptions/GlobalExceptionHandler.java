package com.example.testDependencyService.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Некорректные параметры запроса");
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Внутренняя ошибка сервера");
    }

    @ExceptionHandler(InterruptedException.class)
    public ResponseEntity<?> handleInterruptedException(InterruptedException ex) {
        Thread.currentThread().interrupt(); // Восстанавливаем флаг прерывания
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body("Операция была прервана");
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<?> handleThrowable(Throwable ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Неизвестная ошибка");
    }
}
