package com.Homes2Rent.Homes2Rent.controller;

import com.Homes2Rent.Homes2Rent.exceptions.RecordNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {



        //    Deze exception handler vangt elke RecordNotFoundException op die naar de gebruiker gegooid wordt en returned daar voor in de plaats een ResponseEntity met de Message en de NOT_FOUND-status (404)
        @ExceptionHandler(value = RecordNotFoundException.class)
        public ResponseEntity<Object> exception(RecordNotFoundException exception) {

            return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);

        }

        //    Bonus exception handler
        @ExceptionHandler(value = IndexOutOfBoundsException.class)
        public ResponseEntity<Object> exception(IndexOutOfBoundsException exception) {

            return new ResponseEntity<>("Dit id staat niet in de database", HttpStatus.NOT_FOUND);

        }

    }

