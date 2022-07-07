package com.berk2s.omsapi.infra.adapters.customer;

import com.berk2s.omsapi.domain.customer.model.Customer;
import com.berk2s.omsapi.domain.customer.port.CustomerPort;
import com.berk2s.omsapi.infra.adapters.customer.entity.CustomerEntity;
import com.berk2s.omsapi.infra.adapters.customer.facade.CustomerFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomerAdapter implements CustomerPort {

    private final CustomerFacade customerFacade;

    /**
     * Retrieves Customer
     */
    @Override
    public Customer retrieve(UUID customerId) {
        return customerFacade
                .findByCustomerId(customerId)
                .toModel();
    }

    /**
     * Creates Customer
     */
    @Override
    public Customer create(Customer customer) {
        var customerEntity = new CustomerEntity();
        customerEntity.setFullName(customer.getFullName());

        log.info("Customer has been created. [customerId: {}]", customer.getCustomerId());

        return customerFacade
                .save(customerEntity)
                .toModel();
    }
}
