package com.berk2s.omsapi.infra.adapters.inventory.controller.dtos;

import com.berk2s.omsapi.domain.inventory.model.Inventory;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@Builder
public class InventoryResponse {

    private UUID inventoryId;
    private String barcode;
    private String description;
    private Integer totalQuantity;
    private BigDecimal price;
    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;

    public static InventoryResponse from(Inventory inventory) {
        return InventoryResponse.builder()
                .inventoryId(inventory.getInventoryId())
                .barcode(inventory.getBarcode())
                .description(inventory.getDescription())
                .totalQuantity(inventory.getTotalQuantity())
                .price(inventory.getPrice())
                .createdAt(inventory.getCreatedAt())
                .lastModifiedAt(inventory.getLastModifiedAt())
                .build();
    }

    public static List<InventoryResponse> from(List<Inventory> inventories) {
        return inventories.stream()
                .map(InventoryResponse::from)
                .collect(Collectors.toList());
    }
}
