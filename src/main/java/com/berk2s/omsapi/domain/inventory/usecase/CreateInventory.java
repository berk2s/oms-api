package com.berk2s.omsapi.domain.inventory.usecase;

import com.berk2s.omsapi.domain.usecase.UseCase;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class CreateInventory implements UseCase {

    private String barcode;
    private String description;
    private Integer totalQuantity;
    private BigDecimal price;
}
