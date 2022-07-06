package com.berk2s.omsapi.infra.adapters.order;

import com.berk2s.omsapi.domain.order.model.Order;
import com.berk2s.omsapi.domain.order.model.OrderAddress;
import com.berk2s.omsapi.domain.order.model.OrderLine;
import com.berk2s.omsapi.domain.order.port.OrderPort;
import com.berk2s.omsapi.infra.adapters.customer.facade.CustomerFacade;
import com.berk2s.omsapi.infra.adapters.inventory.facade.InventoryFacade;
import com.berk2s.omsapi.infra.adapters.order.entity.OrderAddressEntity;
import com.berk2s.omsapi.infra.adapters.order.entity.OrderEntity;
import com.berk2s.omsapi.infra.adapters.order.entity.OrderLineEntity;
import com.berk2s.omsapi.infra.adapters.order.facade.OrderFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderAdapter implements OrderPort {

    private final OrderFacade orderFacade;
    private final CustomerFacade customerFacade;
    private final InventoryFacade inventoryFacade;

    @Override
    public Order retrieve(UUID orderId) {
        return orderFacade.findByOrderId(orderId).toModel();
    }

    @Override
    public Order create(Order order) {
        var customer = customerFacade
                .findByCustomerId(order.getCustomer().getCustomerId());

        var orderAddress = toOrderAddress(order.getAddress());

        var orderEntity = new OrderEntity();
        orderEntity.setOrderAddress(orderAddress);
        orderEntity.setCustomer(customer);
        orderEntity.setPrice(order.totalPrice());

        order.getProducts().forEach(product -> {
            var productEntity = inventoryFacade
                    .findByBarcode(product.getBarcode());

            var orderLineEntity = new OrderLineEntity();
            orderLineEntity.setProduct(productEntity);
            orderLineEntity.setBarcode(product.getBarcode());
            orderLineEntity.setDescription(product.getDescription());
            orderLineEntity.setQuantity(product.getQuantity());
            orderLineEntity.setPrice(product.getPrice());

            orderEntity.addOrderLine(orderLineEntity);
        });

        log.info("Order has been created [orderId: {}]", orderEntity.getId());

        return orderFacade.save(orderEntity).toModel();
    }

    @Override
    public Order update(Order order) {
        var customer = customerFacade
                .findByCustomerId(order.getCustomer().getCustomerId());

        var orderAddress = toOrderAddress(order.getAddress());

        var orderEntity = orderFacade.findByOrderId(order.getOrderId());
        orderEntity.setOrderAddress(orderAddress);
        orderEntity.setCustomer(customer);
        orderEntity.setPrice(order.totalPrice());

        order.getProducts().forEach(orderLine -> {
            var orderLineEntityOpt = findOrderLine(orderEntity, orderLine);
            var orderLineEntity = updateOrderLine(orderLine, orderLineEntityOpt);

            orderEntity.addOrderLine(orderLineEntity);
        });

        log.info("Order has been updated [orderId: {}]", orderEntity.getId());

        return orderFacade.save(orderEntity).toModel();
    }

    @Override
    public void delete(UUID orderId) {
        var order = orderFacade.findByOrderId(orderId);

        orderFacade.delete(order);

        log.info("Order has been deleted [orderId: {}]", order.getId());
    }

    @Override
    public boolean existsById(UUID orderId) {
        return orderFacade
                .existsById(orderId);
    }

    private OrderAddressEntity toOrderAddress(OrderAddress address) {
        var orderAddress = new OrderAddressEntity();
        orderAddress.setCity(address.getCity());
        orderAddress.setDistrict(address.getDistrict());
        orderAddress.setCountryCode(address.getCountryCode());
        orderAddress.setPostalCode(address.getPostalCode());
        orderAddress.setPhoneNumber(address.getPhoneNumber());

        return orderAddress;
    }

    private Optional<OrderLineEntity> findOrderLine(OrderEntity orderEntity, OrderLine orderLine) {
        return orderEntity.getOrderLines()
                .stream()
                .filter(i -> i.getBarcode().equals(orderLine.getBarcode()))
                .findFirst();
    }

    private OrderLineEntity updateOrderLine(OrderLine orderLine, Optional<OrderLineEntity> orderLineEntityOpt) {
        OrderLineEntity orderLineEntity;

        if (orderLineEntityOpt.isEmpty()) {
            orderLineEntity = new OrderLineEntity();

            var product = inventoryFacade.findByBarcode(orderLine.getBarcode());
            orderLineEntity.setProduct(product);
        } else {
            orderLineEntity = orderLineEntityOpt.get();
        }

        orderLineEntity.setBarcode(orderLine.getBarcode());
        orderLineEntity.setDescription(orderLine.getDescription());
        orderLineEntity.setPrice(orderLine.getPrice());
        orderLineEntity.setQuantity(orderLine.getQuantity());

        return orderLineEntity;
    }

}
