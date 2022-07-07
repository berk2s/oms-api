package com.berk2s.omsapi.domain.order.usecase;

import com.berk2s.omsapi.domain.usecase.UseCase;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class DeleteOrderLine implements UseCase {

    private UUID orderId;
    private String barcode;

    public static DeleteOrderLine from(UUID orderId, String barcode) {
        return DeleteOrderLine.builder()
                .orderId(orderId)
                .barcode(barcode)
                .build();
    }
}
