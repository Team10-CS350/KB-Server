package com.example.demo.exceptions.types;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatus;

public class PermissionDeniedException extends AbstractApiException {
    public PermissionDeniedException() {
        super(HttpStatus.UNAUTHORIZED, ApiErrors.UNAUTHORIZED);
    }
}