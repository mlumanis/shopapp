package com.shop.app.shop.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.sql.Timestamp;

@Getter
@Setter
public class ApiException {

    private final String message;
    private final HttpStatus httpStatus;
    private final Timestamp timestamp;

    public ApiException(String message, HttpStatus httpStatus, Timestamp timestamp) {
        this.message = message;
        this.httpStatus = httpStatus;
        this.timestamp = timestamp;
    }
}
