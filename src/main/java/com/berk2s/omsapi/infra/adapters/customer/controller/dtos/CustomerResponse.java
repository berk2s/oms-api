package com.berk2s.omsapi.infra.adapters.customer.controller.dtos;

import com.berk2s.omsapi.domain.customer.model.Customer;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CustomerResponse {

    private String customerId;
    private String fullName;
    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;

    public static CustomerResponse from(Customer customer) {
        return CustomerResponse.builder()
                .customerId(customer.getCustomerId().toString())
                .fullName(customer.getFullName())
                .createdAt(customer.getCreatedAt())
                .lastModifiedAt(customer.getLastModifiedAt())
                .build();
    }
}
