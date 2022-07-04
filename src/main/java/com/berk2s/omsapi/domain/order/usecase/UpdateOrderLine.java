package com.berk2s.omsapi.domain.order.usecase;

import com.berk2s.omsapi.domain.usecase.UseCase;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class UpdateOrderLine implements UseCase {

    private UUID orderId;
    private UUID productId;
    private String barcode;
    private String description;
    private BigDecimal price;
    private Integer quantity;
}
