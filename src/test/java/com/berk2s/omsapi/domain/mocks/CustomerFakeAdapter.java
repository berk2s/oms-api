package com.berk2s.omsapi.domain.mocks;

import com.berk2s.omsapi.domain.customer.model.Customer;
import com.berk2s.omsapi.domain.customer.port.CustomerPort;
import com.berk2s.omsapi.domain.order.model.Order;

import java.util.List;
import java.util.UUID;

public class CustomerFakeAdapter implements CustomerPort {
    @Override
    public Customer retrieve(UUID customerId) {
        var order = new Order();
        order.setOrderId(UUID.randomUUID());

        return Customer.builder()
                .customerId(customerId)
                .orders(List.of(order))
                .fullName("John Doe")
                .build();
    }

    @Override
    public Customer create(Customer customer) {
        return Customer.newCustomer(UUID.randomUUID(),
                customer.getFullName());
    }
}
