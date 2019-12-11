package com.example.demo.exceptions.types;

import org.springframework.http.HttpStatus;

public class NotInEventException extends AbstractApiException {
    public NotInEventException() {
        super(HttpStatus.CONFLICT, ApiErrors.NOT_IN_EVENT);
    }
}