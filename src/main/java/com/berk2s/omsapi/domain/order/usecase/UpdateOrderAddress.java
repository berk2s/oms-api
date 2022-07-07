package com.berk2s.omsapi.domain.order.usecase;

import com.berk2s.omsapi.domain.usecase.UseCase;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class UpdateOrderAddress implements UseCase {

    private UUID customerId;
    private UUID orderId;
    private String countryCode;
    private String city;
    private String district;
    private Integer postalCode;
    private String phoneNumber;
}
