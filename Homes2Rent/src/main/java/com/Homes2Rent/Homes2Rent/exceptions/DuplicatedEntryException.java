package com.Homes2Rent.Homes2Rent.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class DuplicatedEntryException extends Throwable {

    public DuplicatedEntryException(String email_already_exists) {

    }

    @ControllerAdvice
    public class ExceptionController {

        @ExceptionHandler(value = RecordNotFoundException.class)
        public ResponseEntity<Object> exception(RecordNotFoundException exception) {

            return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}


