package com.berk2s.omsapi.domain.order.exception;

public class InconsistentPrice extends RuntimeException {
    public InconsistentPrice(String message) {
        super(message);
    }
}
