package com.berk2s.omsapi.domain.inventory.exception;

public class OutOfQuantityException extends RuntimeException {
    public OutOfQuantityException(String message) {
        super(message);
    }
}
