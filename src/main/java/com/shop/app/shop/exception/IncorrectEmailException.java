package com.shop.app.shop.exception;

public  class IncorrectEmailException extends RuntimeException{

    public IncorrectEmailException(String message) {
        super(message);
    }

    public IncorrectEmailException(String message, Throwable cause) {
        super(message, cause);
    }

}
