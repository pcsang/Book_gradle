package com.example.demo.exception;

import lombok.Data;

@Data
//@AllArgsConstructor
public class ExceptionData {
    private final int statusCode;
    private final String status;
    private final String message;
    private final String type;

    public ExceptionData(int statusCode, String status, String message, String type) {
        this.statusCode = statusCode;
        this.status = status;
        this.message = message;
        this.type = type;
    }
}
