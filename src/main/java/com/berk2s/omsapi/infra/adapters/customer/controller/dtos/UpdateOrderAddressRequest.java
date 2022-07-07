package com.berk2s.omsapi.infra.adapters.customer.controller.dtos;

import com.berk2s.omsapi.domain.order.usecase.UpdateOrderAddress;
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
public class UpdateOrderAddressRequest {

    @NotEmpty(message = "countryCode.empty")
    private String countryCode;

    @NotEmpty(message = "city.empty")
    private String city;

    @NotEmpty(message = "district.empty")
    private String district;

    @NotNull(message = "postalCode.empty")
    private Integer postalCode;

    @NotEmpty(message = "phoneNumber.empty")
    private String phoneNumber;

    public UpdateOrderAddress toUseCase(UUID customerId, UUID orderId) {
        return UpdateOrderAddress.builder()
                .customerId(customerId)
                .orderId(orderId)
                .countryCode(countryCode)
                .city(city)
                .district(district)
                .postalCode(postalCode)
                .phoneNumber(phoneNumber)
                .build();
    }
}
