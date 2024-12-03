package com.camperfire.marketflow.exception;

public class ProductOutOfStocksException extends RuntimeException {
    public ProductOutOfStocksException(String message) {
        super(message);
    }
}
