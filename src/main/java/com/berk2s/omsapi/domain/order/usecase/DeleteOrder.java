package com.berk2s.omsapi.domain.order.usecase;

import com.berk2s.omsapi.domain.usecase.UseCase;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class DeleteOrder implements UseCase {

    private UUID orderId;
}
