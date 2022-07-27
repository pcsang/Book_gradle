package com.example.demoBook.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ApiExceptionHandle {

    @ExceptionHandler(value = {ExceptionInput.class})
    public final ResponseEntity<ExceptionData> handleInput(ExceptionInput e) {
        ExceptionData exceptionData = new ExceptionData(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error", e.getMessage(), "Input error");
        return new ResponseEntity<>(exceptionData, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
