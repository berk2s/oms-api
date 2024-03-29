package com.berk2s.omsapi.domain.order.usecase.handler;

import com.berk2s.omsapi.domain.annotations.DomainService;
import com.berk2s.omsapi.domain.customer.port.CustomerPort;
import com.berk2s.omsapi.domain.inventory.port.InventoryPort;
import com.berk2s.omsapi.domain.order.model.Order;
import com.berk2s.omsapi.domain.order.model.OrderAddress;
import com.berk2s.omsapi.domain.order.model.OrderLine;
import com.berk2s.omsapi.domain.order.port.OrderPort;
import com.berk2s.omsapi.domain.order.usecase.CreateOrder;
import com.berk2s.omsapi.domain.usecase.UseCaseHandler;
import lombok.RequiredArgsConstructor;

import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@DomainService
public class CreateOrderUseCaseHandler implements UseCaseHandler<Order, CreateOrder> {

    private final OrderPort orderPort;
    private final CustomerPort customerPort;
    private final InventoryPort inventoryPort;

    /**
     * Handles create order
     * Called outside of domain
     */
    @Override
    public Order handle(CreateOrder createOrder) {
        var customer = customerPort.retrieve(createOrder.getCustomerId());

        var products = createOrder.getProducts()
                .stream()
                .map(toOrderLine())
                .collect(Collectors.toList());

        return orderPort.create(Order.newOrder(customer, fromDeliveryAddress(createOrder.getDeliveryAddress()), products));
    }

    private Function<CreateOrder.OrderProduct, OrderLine> toOrderLine() {
        return orderProduct -> {
            var inventory = inventoryPort.retrieve(orderProduct.getBarcode());
            inventory.reserveQuantity(orderProduct.getRequestedQty());

            inventoryPort.update(inventory);

            return OrderLine.newOrderLine(inventory.getInventoryId(),
                    inventory.getBarcode(),
                    inventory.getDescription(),
                    orderProduct.getRequestedQty(),
                    inventory.getPrice());
        };
    }

    private OrderAddress fromDeliveryAddress(CreateOrder.DeliveryAddress deliveryAddress) {
        return OrderAddress.newAddress(deliveryAddress.getCountryCode(),
                deliveryAddress.getCity(),
                deliveryAddress.getDistrict(),
                deliveryAddress.getPostalCode(),
                deliveryAddress.getPhoneNumber());
    }
}
