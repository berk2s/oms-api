package com.berk2s.omsapi.domain.mocks;

import com.berk2s.omsapi.domain.customer.model.Address;
import com.berk2s.omsapi.domain.customer.model.Customer;
import com.berk2s.omsapi.domain.customer.port.CustomerPort;

import java.util.UUID;

public class CustomerFakeAdapter implements CustomerPort {
    @Override
    public Customer retrieve(UUID customerId) {
        return Customer.newCustomer(customerId,
                "John Doe",
                Address.newAddress("TR", "Izmir", "Konak", 35290, "1112223344"));
    }
}
