package com.example.demo.exception;

public class ExceptionInput extends RuntimeException {
    public ExceptionInput(String message) {
        super(message);
    }

    public ExceptionInput(String message, Throwable throwable) {
        super(message, throwable);
    }
}
