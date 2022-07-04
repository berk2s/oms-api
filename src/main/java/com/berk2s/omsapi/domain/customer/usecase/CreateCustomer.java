package com.berk2s.omsapi.domain.customer.usecase;

import com.berk2s.omsapi.domain.usecase.UseCase;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateCustomer implements UseCase {

    private String fullName;
}
