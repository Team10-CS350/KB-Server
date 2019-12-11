package com.example.demo.exceptions.types;

import org.springframework.http.HttpStatus;

public class AlreadyInEventException extends AbstractApiException {
    public AlreadyInEventException() {
        super(HttpStatus.CONFLICT, ApiErrors.ALREADY_IN_EVENT);
    }
}