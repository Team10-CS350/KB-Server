package com.example.demo.exceptions.types;

import org.springframework.http.HttpStatus;

public class UserAlreadyRegisteredException extends AbstractApiException {
    public UserAlreadyRegisteredException() {
        super(HttpStatus.CONFLICT, ApiErrors.USER_ALREADY_REGISTERED);
    }
}
