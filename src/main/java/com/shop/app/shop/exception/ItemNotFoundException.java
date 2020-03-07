package com.shop.app.shop.exception;

public class ItemNotFoundException extends RuntimeException{

    public ItemNotFoundException(String message) {
        super(message);
    }

    public ItemNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
