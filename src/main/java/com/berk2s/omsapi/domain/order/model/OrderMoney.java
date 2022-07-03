package com.berk2s.omsapi.domain.order.model;

import java.math.BigDecimal;

import static com.berk2s.omsapi.domain.validation.NullValidator.checkNonNull;

public class OrderMoney {
    private BigDecimal price;

    public OrderMoney() {
        price = BigDecimal.ZERO;
    }

    public static OrderMoney of(BigDecimal startMoney) {
        var money = new OrderMoney();
        money.plus(startMoney);

        return money;
    }

    public void plus(BigDecimal givenPrice) {
        checkNonNull(givenPrice);

        price = price.add(givenPrice);
    }

    public void minus(BigDecimal givenPrice) {
        checkNonNull(givenPrice);

        price = price.subtract(givenPrice);
    }

    public BigDecimal price() {
        return price;
    }
}
