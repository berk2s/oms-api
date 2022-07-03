package com.berk2s.omsapi.domain.order.port;

import com.berk2s.omsapi.domain.order.model.Order;
import com.berk2s.omsapi.domain.order.usecase.CreateOrder;

public interface OrderPort {

    Order create(Order order);

}