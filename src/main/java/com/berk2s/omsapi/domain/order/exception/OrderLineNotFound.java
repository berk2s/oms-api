package com.berk2s.omsapi.domain.order.exception;

public class OrderLineNotFound extends RuntimeException {
    public OrderLineNotFound(String message) {
        super(message);
    }
}
