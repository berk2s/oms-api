package com.berk2s.omsapi.infra.adapters.inventory.controller.dtos;

import com.berk2s.omsapi.domain.inventory.usecase.CreateInventory;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
@Builder
public class CreateInventoryRequest {

    @NotEmpty(message = "barcode.empty")
    @Size(min = 1, max = 100, message = "barcode.notInRange")
    private String barcode;

    @NotEmpty(message = "description.empty")
    @Size(min = 1, max = 100, message = "description.notInRange")
    private String description;

    @NotNull(message = "totalQuantity.empty")
    private Integer totalQuantity;

    @NotNull(message = "price.empty")
    private BigDecimal price;

    public CreateInventory toUseCase() {
        return CreateInventory.builder()
                .barcode(barcode)
                .description(description)
                .totalQuantity(totalQuantity)
                .price(price)
                .build();
    }
}
