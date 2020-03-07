package com.shop.app.shop.exception.handler;

import com.shop.app.shop.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.Timestamp;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(value = {InvalidParameterException.class})
    public ResponseEntity<Object> handleExcpetion(InvalidParameterException ex){
        ApiException apiException = new ApiException(ex.getMessage(), HttpStatus.BAD_REQUEST, new Timestamp(System.currentTimeMillis()));
        return new ResponseEntity<Object>(apiException,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {ItemNotFoundException.class})
    public ResponseEntity<Object> handleExcpetion(ItemNotFoundException ex){
        ApiException apiException = new ApiException(ex.getMessage(), HttpStatus.NOT_FOUND, new Timestamp(System.currentTimeMillis()));
        return new ResponseEntity<Object>(apiException,HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(value = {IncorrectEmailException.class})
    public ResponseEntity<Object> handleExcpetion(IncorrectEmailException ex){
        ApiException apiException = new ApiException(ex.getMessage(), HttpStatus.BAD_REQUEST, new Timestamp(System.currentTimeMillis()));
        return new ResponseEntity<Object>(apiException,HttpStatus.NOT_FOUND);
    }
}
