package com.berk2s.omsapi.domain.order.usecase;

import com.berk2s.omsapi.domain.usecase.UseCase;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class CreateOrder implements UseCase {

    private UUID customerId;
    private List<OrderProduct> products;
    private DeliveryAddress deliveryAddress;

    @Getter
    @Builder
    public static class OrderProduct {
        private String barcode;
        /**
         * Requested Quantity means the quantity of product in the order
         */
        private Integer requestedQty;
    }

    @Getter
    @Builder
    public static class DeliveryAddress {
        private String countryCode;
        private String city;
        private String district;
        private Integer postalCode;
        private String phoneNumber;
    }
}
