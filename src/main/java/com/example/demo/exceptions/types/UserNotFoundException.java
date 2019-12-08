package com.example.demo.exceptions.types;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends AbstractApiException {
    public UserNotFoundException() {
        super(HttpStatus.NOT_FOUND, ApiErrors.USER_NOT_FOUND);
    }
}