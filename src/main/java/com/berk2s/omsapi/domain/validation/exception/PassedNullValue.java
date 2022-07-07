package com.berk2s.omsapi.domain.validation.exception;

public class PassedNullValue extends RuntimeException {
    public PassedNullValue(String message) {
        super(message);
    }
}
