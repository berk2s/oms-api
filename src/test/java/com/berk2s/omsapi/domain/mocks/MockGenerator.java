package com.berk2s.omsapi.domain.mocks;

import com.berk2s.omsapi.domain.customer.model.Address;
import com.berk2s.omsapi.domain.customer.model.Customer;

import java.util.UUID;

public final class MockGenerator {

    public static Customer mockCustomer(UUID customerId) {
        return Customer.newCustomer(customerId == null ? UUID.randomUUID() : customerId,
                "John Doe",
                Address.newAddress("TR", "Izmir", "Konak", 35290, "1112223344"));
    }
}
