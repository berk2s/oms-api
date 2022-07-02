package com.berk2s.omsapi.domain.product.exception;

public class InconsistentPrice extends RuntimeException {
    public InconsistentPrice(String message) {
        super(message);
    }
}
