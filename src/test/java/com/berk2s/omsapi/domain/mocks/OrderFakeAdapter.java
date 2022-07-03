package com.berk2s.omsapi.domain.mocks;

import com.berk2s.omsapi.domain.order.model.Order;
import com.berk2s.omsapi.domain.order.port.OrderPort;

public class OrderFakeAdapter implements OrderPort {
    @Override
    public Order create(Order order) {
        return Order.newOrder(order.getCustomer(),
                order.getAddress(),
                order.getProducts());
    }
}
