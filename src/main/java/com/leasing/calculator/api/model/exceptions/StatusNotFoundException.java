package com.leasing.calculator.api.model.exceptions;

import org.springframework.http.HttpStatus;

public class StatusNotFoundException extends AppException {
    public StatusNotFoundException(String id) {
        super("Cannot find a task with id: " + id, HttpStatus.NOT_FOUND);
    }
}
