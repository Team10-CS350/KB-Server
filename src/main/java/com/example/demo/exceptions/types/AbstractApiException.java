package com.example.demo.exceptions.types;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public abstract class AbstractApiException extends Exception {
    private HttpStatus status;

    AbstractApiException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }
}
