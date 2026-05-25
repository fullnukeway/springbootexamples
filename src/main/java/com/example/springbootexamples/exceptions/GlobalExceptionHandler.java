package com.example.springbootexamples.exceptions;


import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@Order(Ordered.HIGHEST_PRECEDENCE)
// ensures that this exception handler is applied before any other handlers (if there are multiple)
@ControllerAdvice       // addresses exceptions across the entire app (globally)
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    // This class can be used to handle exceptions globally across the application. You can define methods to handle specific exceptions and return appropriate responses.

    // 1. Address feedback not found or customer not found

    @ExceptionHandler(ResourceNotFoundException.class)
    protected ResponseEntity<Object> handleResourceNotFound(ResourceNotFoundException ex) {

        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", ex.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    // 2. Address message sent over that is not readable

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        // Use the custom exception, called MessageNotReadableException
        MessageNotReadableException messageNotReadableException = new MessageNotReadableException();

        // store our response as a HashMap
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", messageNotReadableException.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    // 3. Address specific inputs that are NOT valid (Entities Customer and Feedback)
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach(err->{ // we loop through the various validation errors
            String field = ((FieldError) err).getField();   // "firstName"
            String errMessage = err.getDefaultMessage();    // "Min. 2 characters in first name"
            errors.put(field, errMessage);
        });

        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("error", errors);

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

}
