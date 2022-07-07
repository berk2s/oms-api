package com.berk2s.omsapi.domain.mocks;

import com.berk2s.omsapi.domain.customer.model.Customer;
import com.berk2s.omsapi.domain.order.model.Order;
import com.berk2s.omsapi.domain.order.model.OrderAddress;
import com.berk2s.omsapi.domain.order.model.OrderLine;
import com.berk2s.omsapi.domain.order.port.OrderPort;
import org.apache.commons.lang3.RandomStringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OrderFakeAdapter implements OrderPort {
    @Override
    public Order retrieve(UUID orderId) {
        return Order.newOrder(orderId,
                Customer.newCustomer(UUID.randomUUID(), RandomStringUtils.randomAlphabetic(5)),
                OrderAddress.newAddress("TR", "Izmir", "Konak", 35290, "5552221133"),
                new ArrayList<>(
                        List.of(OrderLine.newOrderLine(orderId,
                                "barcode",
                                "desc",
                                54,
                                BigDecimal.valueOf(22)))
                ));
    }

    @Override
    public Order create(Order order) {
        return Order.newOrder(order.getCustomer(),
                order.getAddress(),
                order.getProducts());
    }

    @Override
    public Order update(Order order) {
        return order;
    }

    @Override
    public Order removeOrderLine(Order order) {
        return order;
    }

    @Override
    public void delete(UUID orderId) {

    }

    @Override
    public boolean existsById(UUID orderId) {
        return orderId != null;
    }
}
