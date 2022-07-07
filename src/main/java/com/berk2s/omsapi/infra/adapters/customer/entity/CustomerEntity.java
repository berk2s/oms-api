package com.berk2s.omsapi.infra.adapters.customer.entity;

import com.berk2s.omsapi.domain.customer.model.Customer;
import com.berk2s.omsapi.domain.order.model.Order;
import com.berk2s.omsapi.infra.adapters.order.entity.OrderEntity;
import com.berk2s.omsapi.infra.jpa.UUIDIdentifierEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
public class CustomerEntity extends UUIDIdentifierEntity {

    @Column
    private String fullName;

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderEntity> orders = new ArrayList<>();

    public Customer toModel() {
        return Customer.builder()
                .customerId(getId())
                .orders(orders.stream().map(OrderEntity::toModel).collect(Collectors.toList()))
                .fullName(fullName)
                .createdAt(getCreatedAt())
                .lastModifiedAt(getLastModifiedAt())
                .build();
    }

    public Customer toModelWithoutOrders() {
        return Customer.builder()
                .customerId(getId())
                .orders(null)
                .fullName(fullName)
                .createdAt(getCreatedAt())
                .lastModifiedAt(getLastModifiedAt())
                .build();
    }
}
