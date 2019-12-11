package com.example.demo.exceptions;

import com.example.demo.exceptions.types.*;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String error = "Malformed JSON request";
        return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, error, ex));
    }

    // TODO: use generics/wildcards

    @ExceptionHandler(UserNotFoundException.class)
    protected ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException ex) {
        return handleGenericException(ex);
    }

    @ExceptionHandler(UserAlreadyRegisteredException.class)
    protected ResponseEntity<Object> handleUserAlreadyRegisteredException(UserAlreadyRegisteredException ex) {
        return handleGenericException(ex);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        BindingResult bindingResult = ex.getBindingResult();

        List<FieldError> fieldErrors = bindingResult.getFieldErrors();

        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);

        // TODO: return a list instead
        if (fieldErrors.size() == 0) {
            List<ObjectError> allErrors = bindingResult.getAllErrors();

            apiError.setMessage(allErrors.stream().map(
                    error -> error.getDefaultMessage()
            ).collect(Collectors.joining("\n")));
        } else {
            apiError.setMessage(fieldErrors.stream().map(
                    fieldError -> fieldError.getField() + " field " + fieldError.getDefaultMessage()
            ).collect(Collectors.joining("\n")));
        }

        return buildResponseEntity(apiError);
    }

    private static ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    private static ResponseEntity<Object> handleGenericException(AbstractApiException exception) {
        ApiError apiError = new ApiError(exception.getStatus());
        apiError.setMessage(exception.getMessage());
        return buildResponseEntity(apiError);
    }
}