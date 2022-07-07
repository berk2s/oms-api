package com.berk2s.omsapi.infra.adapters.customer.facade;

import com.berk2s.omsapi.infra.adapters.customer.entity.CustomerEntity;
import com.berk2s.omsapi.infra.adapters.customer.repository.CustomerRepository;
import com.berk2s.omsapi.infra.exception.EntityNotFound;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomerFacade {

    private final CustomerRepository customerRepository;

    public CustomerEntity findByCustomerId(UUID customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> {
                   log.warn("Customer with given id does not exists [customerId: {}]", customerId);
                   throw new EntityNotFound("customer.notFound");
                });
    }

    public CustomerEntity save(CustomerEntity customer) {
        return customerRepository.save(customer);
    }

}
