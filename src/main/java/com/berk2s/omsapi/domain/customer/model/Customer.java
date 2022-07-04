package com.berk2s.omsapi.domain.customer.model;

import com.berk2s.omsapi.domain.customer.exception.FakeName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@Getter
@Builder
public class Customer {

    private UUID customerId;
    private String fullName;
    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;

    public static Customer newCustomer(UUID customerId, String fullName) {
        var customer = new Customer(customerId, fullName, null, null);
        customer.validate();

        return customer;
    }

    public static Customer newCustomer(String fullName) {
        var customer = new Customer(null, fullName, null, null);
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
