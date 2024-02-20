package com.ChaTop.Rental.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.ChaTop.Rental.exception.BadCredentialsCustomException;
import com.ChaTop.Rental.exception.RentalNotFoundException;
import com.ChaTop.Rental.exception.UserAlreadyExistsException;
import com.ChaTop.Rental.exception.UserNotFoundException;

@ControllerAdvice
public class ExceptionController {

    private static final String MESSAGE = "message";

    private static final Logger log = LoggerFactory.getLogger(ExceptionController.class);


    @ExceptionHandler(UserAlreadyExistsException.class) 
    public ResponseEntity<Object> exceptionHandler(UserAlreadyExistsException ex, WebRequest request){
        Map<String, Object> body = new LinkedHashMap<>();
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsCustomException.class) 
    public ResponseEntity<Object> exceptionHandler(BadCredentialsCustomException ex, WebRequest request){
        Map<String, Object> body = new LinkedHashMap<>();
        body.put(MESSAGE, ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UserNotFoundException.class) 
    public ResponseEntity<Object> exceptionHandler(UserNotFoundException ex, WebRequest request){
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RentalNotFoundException.class) 
    public ResponseEntity<Object> exceptionHandler(RentalNotFoundException ex, WebRequest request){
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
}
