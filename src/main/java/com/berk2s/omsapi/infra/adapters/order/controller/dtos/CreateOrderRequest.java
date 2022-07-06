package com.berk2s.omsapi.infra.adapters.order.controller.dtos;

import com.berk2s.omsapi.domain.order.usecase.CreateOrder;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CreateOrderRequest {

    @NotNull(message = "products.empty")
    @Size(min = 1, message = "product.notInRange")
    private List<OrderProduct> products;

    @NotNull(message = "deliveryAddress.empty")
    private DeliveryAddress deliveryAddress;

    public CreateOrder toUseCase(UUID customerId) {
        return CreateOrder.builder()
                .customerId(customerId)
                .products(products.stream().map(i -> OrderProduct.toUseCase(i.barcode, i.getRequestedQty())).collect(Collectors.toList()))
                .deliveryAddress(deliveryAddress.toUseCase(deliveryAddress.getCountryCode(),
                        deliveryAddress.getCity(), deliveryAddress.getDistrict(),
                        deliveryAddress.getPostalCode(),
                        deliveryAddress.getPhoneNumber()))
                .build();
    }

    @AllArgsConstructor
    @Getter
    @Builder
    public static class OrderProduct {
        private String barcode;
        private Integer requestedQty;

        public static CreateOrder.OrderProduct toUseCase(String barcode, Integer qty) {
            return CreateOrder.OrderProduct.builder()
                    .barcode(barcode)
                    .requestedQty(qty)
                    .build();
        }
    }

    @AllArgsConstructor
    @Getter
    @Builder
    public static class DeliveryAddress {
        private String countryCode;
        private String city;
        private String district;
        private Integer postalCode;
        private String phoneNumber;

        public CreateOrder.DeliveryAddress toUseCase(String countryCode,
                                                     String city,
                                                     String district,
                                                     Integer postalCode,
                                                     String phoneNumber) {
            return CreateOrder.DeliveryAddress.builder()
                    .countryCode(countryCode)
                    .city(city)
                    .district(district)
                    .postalCode(postalCode)
                    .phoneNumber(phoneNumber)
                    .build();
        }
    }
}
