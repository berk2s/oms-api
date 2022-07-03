package com.berk2s.omsapi.domain.customer.model;

import com.berk2s.omsapi.domain.customer.exception.FakeName;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class Customer {

    private UUID customerId;
    private String fullName;
    private Address address;

    public static Customer newCustomer(UUID customerId, String fullName,
                                  Address address) {
        var customer = new Customer(customerId, fullName, address);
        customer.validate();

        return customer;
    }

    public static Customer newCustomer(String fullName, Address address) {
        var customer = new Customer(null, fullName, address);
        customer.validate();

        return customer;
    }

    public void validate() {
        if (fullName.length() < 2) {
            throw new FakeName("Given name is not real");
        }
    }
}
