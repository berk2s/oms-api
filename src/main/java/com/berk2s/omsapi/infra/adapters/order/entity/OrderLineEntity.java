package com.berk2s.omsapi.infra.adapters.order.entity;

import com.berk2s.omsapi.domain.inventory.model.Inventory;
import com.berk2s.omsapi.domain.order.model.OrderLine;
import com.berk2s.omsapi.infra.adapters.inventory.entity.InventoryEntity;
import com.berk2s.omsapi.infra.jpa.LongIdentifierEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
public class OrderLineEntity extends LongIdentifierEntity {

    @Column(unique = true)
    private String barcode;

    @Column
    private String description;

    @Column
    private Integer quantity;

    @Column
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE, optional = false)
    private InventoryEntity product;

    public OrderLine toModel() {
        return OrderLine.newOrderLine(
                product.getId(),
                barcode,
                description,
                quantity,
                price);
    }
}
