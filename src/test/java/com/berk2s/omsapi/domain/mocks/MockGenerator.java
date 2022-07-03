package com.berk2s.omsapi.domain.mocks;

import com.berk2s.omsapi.domain.customer.model.Customer;
import com.berk2s.omsapi.domain.order.model.Order;
import com.berk2s.omsapi.domain.order.model.OrderAddress;
import com.berk2s.omsapi.domain.order.model.OrderLine;
import org.apache.commons.lang3.RandomStringUtils;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Collectors;

public final class MockGenerator {

    public static Customer mockCustomer(UUID customerId) {
        return Customer.newCustomer(customerId == null ? UUID.randomUUID() : customerId,
                "John Doe");
    }

    public static Order mockOrder(Integer... price) {
        var orderLines = Arrays.stream(price)
                .map(_price ->  mockOrderLine(5, BigDecimal.valueOf(_price)))
                .collect(Collectors.toList());

        return Order.newOrder(UUID.randomUUID(),
                mockCustomer(null), mockAddress(), orderLines);
    }

    public static OrderLine mockOrderLine(Integer qty, BigDecimal price) {
        return OrderLine.newOrderLine(UUID.randomUUID(),
                RandomStringUtils.randomAlphabetic(8),
                RandomStringUtils.randomAlphabetic(8),
                qty,
                price);
    }

    public static OrderAddress mockAddress() {
        return OrderAddress.newAddress("TR", "Izmir", "Konak", 35290, "1112223344");
    }
}
