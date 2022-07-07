package com.berk2s.omsapi.infra.adapters.order.entity;

import com.berk2s.omsapi.domain.order.model.Order;
import com.berk2s.omsapi.infra.adapters.customer.entity.CustomerEntity;
import com.berk2s.omsapi.infra.jpa.UUIDIdentifierEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
public class OrderEntity extends UUIDIdentifierEntity {

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private CustomerEntity customer;

    @Embedded
    private OrderAddressEntity orderAddress;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderLineEntity> orderLines = new ArrayList<>();

    private BigDecimal price;

    public void addOrderLine(OrderLineEntity orderLine) {
        if (!orderLines.contains(orderLine)) {
            orderLines.add(orderLine);
        }
    }

    public void removeOrderLine(OrderLineEntity orderLine) {
        orderLines.remove(orderLine);
    }

    public Order toModel() {
        return Order.newOrder(getId(),
                customer.toModelWithoutOrders(),
                orderAddress.toModel(),
                orderLines.stream().map(OrderLineEntity::toModel).collect(Collectors.toList()));
    }
}
