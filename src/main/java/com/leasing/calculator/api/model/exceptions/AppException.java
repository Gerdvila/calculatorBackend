package com.leasing.calculator.api.model.exceptions;

import org.springframework.http.HttpStatus;

public class AppException extends RuntimeException {

    private final HttpStatus httpStatus;
    private final String logMessage;

    public AppException(String message, HttpStatus httpStatus, String logMessage) {
        super(message);
        this.httpStatus = httpStatus;
        this.logMessage = logMessage;
    }

    public AppException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
        this.logMessage = null;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getLogMessage() {
        return logMessage;
    }

}