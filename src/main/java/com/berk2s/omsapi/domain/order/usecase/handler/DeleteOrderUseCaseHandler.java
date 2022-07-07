package com.berk2s.omsapi.domain.order.usecase.handler;

import com.berk2s.omsapi.domain.annotations.DomainService;
import com.berk2s.omsapi.domain.order.exception.OrderNotFound;
import com.berk2s.omsapi.domain.order.model.Order;
import com.berk2s.omsapi.domain.order.port.OrderPort;
import com.berk2s.omsapi.domain.order.usecase.DeleteOrder;
import com.berk2s.omsapi.domain.usecase.UseCaseHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@DomainService
public class DeleteOrderUseCaseHandler implements UseCaseHandler<Order, DeleteOrder> {

    private final OrderPort orderPort;

    @Override
    public Order handle(DeleteOrder deleteOrder) {
        if (!orderPort.existsById(deleteOrder.getOrderId())) {
            log.warn("Order with given id does not exists [orderId: {}]", deleteOrder.getOrderId());
            throw new OrderNotFound("order.notFound");
        }

        orderPort.delete(deleteOrder.getOrderId());

        log.info("Order deleted therefore order lines deleted [orderId: {}]", deleteOrder.getOrderId());

        return Order.empty();
    }
}
