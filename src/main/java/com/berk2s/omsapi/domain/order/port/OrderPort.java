package com.berk2s.omsapi.domain.order.port;

import com.berk2s.omsapi.domain.order.model.Order;

public interface OrderPort {

    Order create(Order order);

}