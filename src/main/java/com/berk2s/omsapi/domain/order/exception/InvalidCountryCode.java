package com.berk2s.omsapi.domain.order.exception;

public class InvalidCountryCode extends RuntimeException {
    public InvalidCountryCode(String s) {
        super(s);
    }
}
