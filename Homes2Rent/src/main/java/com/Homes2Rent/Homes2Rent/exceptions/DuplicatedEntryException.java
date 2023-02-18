package com.Homes2Rent.Homes2Rent.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


public class DuplicatedEntryException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public DuplicatedEntryException(String username) {
        super("Cannot find user " + username);
    }

}










