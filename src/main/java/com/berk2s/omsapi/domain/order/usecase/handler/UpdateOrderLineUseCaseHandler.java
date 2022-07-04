package com.berk2s.omsapi.domain.order.usecase.handler;

import com.berk2s.omsapi.domain.inventory.port.InventoryPort;
import com.berk2s.omsapi.domain.order.exception.InvalidQuantityState;
import com.berk2s.omsapi.domain.order.model.Order;
import com.berk2s.omsapi.domain.order.model.OrderLine;
import com.berk2s.omsapi.domain.order.port.OrderPort;
import com.berk2s.omsapi.domain.order.usecase.UpdateOrderLine;
import com.berk2s.omsapi.domain.usecase.UseCaseHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class UpdateOrderLineUseCaseHandler implements UseCaseHandler<Order, UpdateOrderLine> {

    private final OrderPort orderPort;
    private final InventoryPort inventoryPort;

    @Override
    public Order handle(UpdateOrderLine updateOrderLine) {
        var inventory = inventoryPort.retrieve(updateOrderLine.getBarcode());
        inventory.reserveQuantity(updateOrderLine.getQuantity());

        var order = orderPort.retrieve(updateOrderLine.getOrderId());

        var updatedProduct = OrderLine.newOrderLine(updateOrderLine.getProductId(),
                updateOrderLine.getBarcode(),
                updateOrderLine.getDescription(),
                updateOrderLine.getQuantity(),
                updateOrderLine.getPrice());

        order.updateProduct(updatedProduct);

        return orderPort.update(order);
    }
}
