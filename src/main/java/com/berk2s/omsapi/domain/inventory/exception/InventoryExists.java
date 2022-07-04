package com.berk2s.omsapi.domain.inventory.exception;

public class InventoryExists extends RuntimeException {
    public InventoryExists(String message) {
        super(message);
    }
}
