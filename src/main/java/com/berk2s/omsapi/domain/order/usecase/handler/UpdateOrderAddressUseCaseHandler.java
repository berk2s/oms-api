package com.berk2s.omsapi.domain.order.usecase.handler;

import com.berk2s.omsapi.domain.order.model.Order;
import com.berk2s.omsapi.domain.order.model.OrderAddress;
import com.berk2s.omsapi.domain.order.port.OrderPort;
import com.berk2s.omsapi.domain.order.usecase.UpdateOrderAddress;
import com.berk2s.omsapi.domain.usecase.UseCaseHandler;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UpdateOrderAddressUseCaseHandler implements UseCaseHandler<Order, UpdateOrderAddress> {

    private final OrderPort orderPort;

    @Override
    public Order handle(UpdateOrderAddress updateOrderAddress) {
        var order = orderPort.retrieve(updateOrderAddress.getOrderId());

        var address = OrderAddress.newAddress(updateOrderAddress.getCountryCode(),
                updateOrderAddress.getCity(),
                updateOrderAddress.getDistrict(),
                updateOrderAddress.getPostalCode(),
                updateOrderAddress.getPhoneNumber());

        order.updateAddress(address);

        return orderPort.update(order);
    }
}
