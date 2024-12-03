package com.camperfire.marketflow.exception;

public class NotEnoughProductQuantityException extends RuntimeException {
    public NotEnoughProductQuantityException(String message) {
        super(message);
    }
}
