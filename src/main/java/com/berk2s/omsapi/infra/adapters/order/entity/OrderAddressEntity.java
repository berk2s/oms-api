package com.berk2s.omsapi.infra.adapters.order.entity;

import com.berk2s.omsapi.domain.order.model.OrderAddress;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Getter
@Setter
@Embeddable
public class OrderAddressEntity {

    @Column
    private String countryCode;

    @Column
    private String city;

    @Column
    private String district;

    @Column
    private Integer postalCode;

    @Column
    private String phoneNumber;

    public OrderAddress toModel() {
        return OrderAddress.newAddress(countryCode, city, district, postalCode, phoneNumber);
    }
}
