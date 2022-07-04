package com.berk2s.omsapi.generators;

import com.berk2s.omsapi.infra.adapters.customer.entity.CustomerEntity;
import org.apache.commons.lang3.RandomStringUtils;

import java.time.LocalDateTime;
import java.util.UUID;

public final class MockGenerator {

    public static CustomerEntity mockCustomerEntity(UUID id) {
        var customer = new CustomerEntity();
        customer.setId(id == null ? UUID.randomUUID() : id);
        customer.setFullName(RandomStringUtils.randomAlphabetic(5));
        customer.setCreatedAt(LocalDateTime.now());
        customer.setLastModifiedAt(LocalDateTime.now());

        return customer;
    }

}
