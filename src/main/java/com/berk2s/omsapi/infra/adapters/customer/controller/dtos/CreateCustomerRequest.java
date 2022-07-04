package com.berk2s.omsapi.infra.adapters.customer.controller.dtos;

import com.berk2s.omsapi.domain.customer.usecase.CreateCustomer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CreateCustomerRequest {

    @NotEmpty(message = "fullName.empty")
    @Size(min = 1, max = 100, message = "fullName.notInRange")
    private String fullName;

    public CreateCustomer toUseCase() {
        return CreateCustomer.builder()
                .fullName(fullName)
                .build();
    }
}
