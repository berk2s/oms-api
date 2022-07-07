package com.berk2s.omsapi.infra.adapters.customer.controller.dtos;

import com.berk2s.omsapi.domain.order.usecase.UpdateOrderLine;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UpdateOrderLineRequest {

    @NotNull(message = "quantity.empty")
    private Integer quantity;

    public UpdateOrderLine toUseCase(UUID customerId, UUID orderId, String barcode) {
        return UpdateOrderLine.builder()
                .customerId(customerId)
                .orderId(orderId)
                .barcode(barcode)
                .quantity(quantity)
                .build();
    }
}
