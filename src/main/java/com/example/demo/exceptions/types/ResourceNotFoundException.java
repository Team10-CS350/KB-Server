package com.example.demo.exceptions.types;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends AbstractApiException {
    public ResourceNotFoundException() {
        super(HttpStatus.NOT_FOUND, ApiErrors.RESOURCE_NOT_FOUND);
    }
}