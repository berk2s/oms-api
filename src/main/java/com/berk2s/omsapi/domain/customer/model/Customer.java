package com.berk2s.omsapi.domain.customer.model;

import com.berk2s.omsapi.domain.customer.exception.FakeName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
@AllArgsConstructor
@Getter
public class Customer {

    private UUID customerId;
    private String fullName;

    public static Customer newCustomer(UUID customerId, String fullName) {
        var customer = new Customer(customerId, fullName);
        customer.validate();

        return customer;
    }

    public static Customer newCustomer(String fullName) {
        var customer = new Customer(null, fullName);
        customer.validate();

        return customer;
    }

    public void validate() {
        if (fullName.length() < 2) {
            log.warn("Invalid or fake full name [fullName: {}]", fullName);
            throw new FakeName("fullName.invalid");
        }
    }
}
