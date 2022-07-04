package com.berk2s.omsapi.infra.adapters.customer.entity;

import com.berk2s.omsapi.domain.customer.model.Customer;
import com.berk2s.omsapi.infra.jpa.UUIDIdentifierEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;

@Getter
@Setter
@Entity
public class CustomerEntity extends UUIDIdentifierEntity {

    @Column
    private String fullName;

    public Customer toModel() {
        return Customer.builder()
                .customerId(getId())
                .fullName(fullName)
                .createdAt(getCreatedAt())
                .lastModifiedAt(getLastModifiedAt())
                .build();
    }
}
