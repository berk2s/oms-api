package com.berk2s.omsapi.domain.order.port;

import com.berk2s.omsapi.domain.order.model.Order;
import org.aspectj.weaver.ast.Or;

import java.util.UUID;

public interface OrderPort {

    Order retrieve(UUID orderId);

    Order create(Order order);

    Order update(Order order);

    Order removeOrderLine(Order order);

    void delete(UUID orderId);

    boolean existsById(UUID orderId);
}