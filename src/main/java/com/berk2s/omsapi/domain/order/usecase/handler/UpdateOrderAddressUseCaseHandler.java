package com.berk2s.omsapi.domain.order.usecase.handler;

import com.berk2s.omsapi.domain.annotations.DomainService;
import com.berk2s.omsapi.domain.customer.port.CustomerPort;
import com.berk2s.omsapi.domain.order.model.Order;
import com.berk2s.omsapi.domain.order.model.OrderAddress;
import com.berk2s.omsapi.domain.order.port.OrderPort;
import com.berk2s.omsapi.domain.order.usecase.UpdateOrderAddress;
import com.berk2s.omsapi.domain.usecase.UseCaseHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@DomainService
public class UpdateOrderAddressUseCaseHandler implements UseCaseHandler<Order, UpdateOrderAddress> {

    private final OrderPort orderPort;
    private final CustomerPort customerPort;

    @Override
    public Order handle(UpdateOrderAddress updateOrderAddress) {
        var customer = customerPort.retrieve(updateOrderAddress.getCustomerId());
        customer.isOrderValid(updateOrderAddress.getOrderId());

        var order = orderPort.retrieve(updateOrderAddress.getOrderId());

        var address = OrderAddress.newAddress(updateOrderAddress.getCountryCode(),
                updateOrderAddress.getCity(),
                updateOrderAddress.getDistrict(),
                updateOrderAddress.getPostalCode(),
                updateOrderAddress.getPhoneNumber());

        order.updateAddress(address);

        log.info("ORder address has been updated [customerId: {}, orderId: {}]",
                customer.getCustomerId(), order.getOrderId());

        return orderPort.update(order);
    }
}
