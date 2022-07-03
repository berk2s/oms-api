package com.berk2s.omsapi.domain.customer.usecase.handler;

import com.berk2s.omsapi.domain.customer.model.Address;
import com.berk2s.omsapi.domain.customer.model.Customer;
import com.berk2s.omsapi.domain.customer.port.CustomerPort;
import com.berk2s.omsapi.domain.customer.usecase.CreateCustomer;
import com.berk2s.omsapi.domain.usecase.UseCaseHandler;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreateCustomerUseCaseHandler implements UseCaseHandler<Customer, CreateCustomer> {

    private final CustomerPort customerPort;

    @Override
    public Customer handle(CreateCustomer createCustomer) {
        var address = Address.newAddress(createCustomer.getCountryCode(),
                createCustomer.getCity(), createCustomer.getDistrict(),
                createCustomer.getPostalCode(), createCustomer.getPhoneNumber());

        var customer = Customer.newCustomer(createCustomer.getFullName(), address);

        return customerPort.create(customer);
    }
}
