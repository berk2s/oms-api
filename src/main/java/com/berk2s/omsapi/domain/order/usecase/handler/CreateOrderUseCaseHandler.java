package com.berk2s.omsapi.domain.order.usecase.handler;

import com.berk2s.omsapi.domain.customer.port.CustomerPort;
import com.berk2s.omsapi.domain.order.model.Order;
import com.berk2s.omsapi.domain.order.port.OrderPort;
import com.berk2s.omsapi.domain.order.usecase.CreateOrder;
import com.berk2s.omsapi.domain.product.port.ProductPort;
import com.berk2s.omsapi.domain.usecase.UseCaseHandler;
import lombok.RequiredArgsConstructor;

import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CreateOrderUseCaseHandler implements UseCaseHandler<Order, CreateOrder> {

    private final OrderPort orderPort;
    private final CustomerPort customerPort;
    private final ProductPort productPort;

    /**
     * Handles create order
     * Called outside of domain
     */
    @Override
    public Order handle(CreateOrder createOrder) {
        var customer = customerPort.retrieve(createOrder.getCustomerId());

        var products = createOrder.getProducts()
                .stream()
                .map(productPort::retrieve)
                .collect(Collectors.toList());

        return orderPort.create(Order.newOrder(customer, products));
    }
}
