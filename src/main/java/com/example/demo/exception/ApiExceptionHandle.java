package com.example.demo.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class ApiExceptionHandle {

    @ExceptionHandler(value = {ExceptionInput.class})
    public final ResponseEntity<ExceptionData> handleInput(ExceptionInput exceptionInput) {
        ExceptionData exceptionData = new ExceptionData(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error", exceptionInput.getMessage(), "Input error");
        return new ResponseEntity<>(exceptionData, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
