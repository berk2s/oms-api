package com.berk2s.omsapi.domain.customer.model;

import com.berk2s.omsapi.domain.customer.exception.FakeName;
import com.berk2s.omsapi.domain.order.model.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@Getter
@Builder
public class Customer {

    private UUID customerId;
    private List<Order> orders;
    private String fullName;
    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;

    public static Customer newCustomer(UUID customerId, String fullName) {
        var customer = new Customer(customerId, null, fullName,null, null);
        customer.validate();

        return customer;
    }

    public static Customer newCustomer(String fullName) {
        var customer = new Customer(null, null, fullName, null, null);
        customer.validate();

        return customer;
    }

    public boolean isOrderValid(UUID orderId) {
        return orders.stream().anyMatch(i -> i.getOrderId().equals(orderId));
    }

    public void validate() {
        if (fullName.length() < 2) {
            log.warn("Invalid or fake full name [fullName: {}]", fullName);
            throw new FakeName("fullName.invalid");
        }
    }
}
