package com.berk2s.omsapi.generators;

import com.berk2s.omsapi.domain.order.model.OrderLine;
import com.berk2s.omsapi.infra.adapters.customer.entity.CustomerEntity;
import com.berk2s.omsapi.infra.adapters.inventory.entity.InventoryEntity;
import com.berk2s.omsapi.infra.adapters.order.entity.OrderAddressEntity;
import com.berk2s.omsapi.infra.adapters.order.entity.OrderEntity;
import com.berk2s.omsapi.infra.adapters.order.entity.OrderLineEntity;
import org.apache.commons.lang3.RandomStringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
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

    public static OrderEntity mockOrderEntity(UUID id) {
        var orderEntity = new OrderEntity();
        orderEntity.setId(id == null ? UUID.randomUUID() : id);
        orderEntity.setOrderAddress(mockOrderAddressEntity());
        orderEntity.setPrice(BigDecimal.valueOf(10));
        orderEntity.setOrderAddress(mockOrderAddressEntity());
        orderEntity.addOrderLine(mockOrderLineEntity());
        orderEntity.setCustomer(mockCustomerEntity(null));

        return orderEntity;
    }

    public static OrderAddressEntity mockOrderAddressEntity() {
        var orderAddress = new OrderAddressEntity();
        orderAddress.setPostalCode(35120);
        orderAddress.setPhoneNumber(RandomStringUtils.randomAlphabetic(10));
        orderAddress.setCity(RandomStringUtils.randomAlphabetic(5));
        orderAddress.setDistrict(RandomStringUtils.randomAlphabetic(5));
        orderAddress.setCountryCode(RandomStringUtils.randomAlphabetic(2));

        return orderAddress;
    }

    public static OrderLineEntity mockOrderLineEntity() {
        var orderLine = new OrderLineEntity();
        orderLine.setId(1L);
        orderLine.setBarcode(RandomStringUtils.randomAlphabetic(5));
        orderLine.setDescription(RandomStringUtils.randomAlphabetic(5));
        orderLine.setQuantity(5);
        orderLine.setPrice(BigDecimal.valueOf(5));
        orderLine.setProduct(mockInventoryEntity(null));

        return orderLine;
    }

}
