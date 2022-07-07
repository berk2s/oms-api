package com.berk2s.omsapi.infra.adapters.customer.controller.dtos;

import com.berk2s.omsapi.domain.order.usecase.UpdateOrderLine;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AddOrderLineRequest {

    @NotEmpty(message = "barcode.empty")
    private String barcode;

    @NotNull(message = "quantity.empty")
    private Integer quantity;

    public UpdateOrderLine toUseCase(UUID customerId, UUID orderId) {
        return UpdateOrderLine.builder()
                .customerId(customerId)
                .orderId(orderId)
                .barcode(barcode)
                .quantity(quantity)
                .build();
    }
}
