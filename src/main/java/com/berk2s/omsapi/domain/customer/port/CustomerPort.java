package com.berk2s.omsapi.domain.customer.port;

import com.berk2s.omsapi.domain.customer.model.Customer;

import java.util.UUID;

public interface CustomerPort {

    Customer retrieve(UUID customerId);

    Customer create(Customer customer);
}
