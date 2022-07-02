package com.berk2s.omsapi.domain.order.exception;

public class EmptyProductState extends RuntimeException {
    public EmptyProductState(String message) {
        super(message);
    }
}
