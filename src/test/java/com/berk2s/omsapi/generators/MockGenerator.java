package com.berk2s.omsapi.generators;

import com.berk2s.omsapi.infra.adapters.customer.entity.CustomerEntity;
import com.berk2s.omsapi.infra.adapters.inventory.entity.InventoryEntity;
import org.apache.commons.lang3.RandomStringUtils;

import java.math.BigDecimal;
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

    public static InventoryEntity mockInventoryEntity(UUID id) {
        var inventory = new InventoryEntity();
        inventory.setId(id == null ? UUID.randomUUID() : id);
        inventory.setBarcode(RandomStringUtils.randomAlphabetic(10));
        inventory.setDescription(RandomStringUtils.randomAlphabetic(10));
        inventory.setPrice(BigDecimal.valueOf(10));
        inventory.setTotalQuantity(10);

        return inventory;
    }

}
