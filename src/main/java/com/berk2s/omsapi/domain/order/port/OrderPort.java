package com.berk2s.omsapi.domain.order.port;

import com.berk2s.omsapi.domain.order.model.Order;

import java.util.UUID;

public interface OrderPort {

    Order create(Order order);

    void delete(UUID orderId);

    boolean existsById(UUID orderId);
}