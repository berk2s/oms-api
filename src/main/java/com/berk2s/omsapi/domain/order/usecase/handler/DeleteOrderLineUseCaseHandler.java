package com.berk2s.omsapi.domain.order.usecase.handler;

import com.berk2s.omsapi.domain.annotations.DomainService;
import com.berk2s.omsapi.domain.inventory.port.InventoryPort;
import com.berk2s.omsapi.domain.order.model.Order;
import com.berk2s.omsapi.domain.order.model.OrderLine;
import com.berk2s.omsapi.domain.order.port.OrderPort;
import com.berk2s.omsapi.domain.order.usecase.DeleteOrderLine;
import com.berk2s.omsapi.domain.usecase.UseCaseHandler;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@DomainService
public class DeleteOrderLineUseCaseHandler implements UseCaseHandler<Order, DeleteOrderLine> {

    private final OrderPort orderPort;
    private final InventoryPort inventoryPort;

    @Override
    public Order handle(DeleteOrderLine deleteOrderLine) {
        var inventory = inventoryPort.retrieve(deleteOrderLine.getBarcode());
        var order = orderPort.retrieve(deleteOrderLine.getOrderId());

        var productToBeRemoved = OrderLine.newOrderLine(inventory.getInventoryId(),
                deleteOrderLine.getBarcode(),
                inventory.getDescription(),
                inventory.getTotalQuantity(),
                inventory.getPrice());

        order.removeProduct(productToBeRemoved);

        return orderPort.removeOrderLine(order);
    }
}
