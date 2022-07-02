package com.berk2s.omsapi.domain.order.usecase;

import com.berk2s.omsapi.domain.usecase.UseCase;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class CreateOrder implements UseCase {

    private UUID customerId;
    private List<String> products;
}
