package com.berk2s.omsapi.domain.customer.usecase.handler;

import com.berk2s.omsapi.domain.annotations.DomainService;
import com.berk2s.omsapi.domain.customer.model.Customer;
import com.berk2s.omsapi.domain.customer.port.CustomerPort;
import com.berk2s.omsapi.domain.customer.usecase.CreateCustomer;
import com.berk2s.omsapi.domain.usecase.UseCaseHandler;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@DomainService
public class CreateCustomerUseCaseHandler implements UseCaseHandler<Customer, CreateCustomer> {

    private final CustomerPort customerPort;

    @Override
    public Customer handle(CreateCustomer createCustomer) {
        var customer = Customer.newCustomer(createCustomer.getFullName());

        return customerPort.create(customer);
    }
}
