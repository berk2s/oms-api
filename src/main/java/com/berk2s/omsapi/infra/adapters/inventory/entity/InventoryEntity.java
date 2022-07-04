package com.berk2s.omsapi.infra.adapters.inventory.entity;

import com.berk2s.omsapi.domain.inventory.model.Inventory;
import com.berk2s.omsapi.infra.jpa.UUIDIdentifierEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
public class InventoryEntity extends UUIDIdentifierEntity {

    @Column
    private String barcode;

    @Column
    private String description;

    @Column
    private Integer totalQuantity;

    @Column
    private BigDecimal price;

    public Inventory toModel() {
        return Inventory.builder()
                .inventoryId(getId())
                .barcode(barcode)
                .description(description)
                .totalQuantity(totalQuantity)
                .price(price)
                .createdAt(getCreatedAt())
                .lastModifiedAt(getLastModifiedAt())
                .build();
    }
}
