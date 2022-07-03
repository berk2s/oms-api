package com.berk2s.omsapi.domain.validation;

import com.berk2s.omsapi.domain.order.exception.InconsistentPrice;
import com.berk2s.omsapi.domain.order.exception.InvalidQuantityState;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

@Slf4j
public final class InventoryValidator {

    public static void validateProduct(Integer totalQty, BigDecimal price) {
        if (totalQty <= 0) {
            log.warn("Given total quantity is invalid [givenQty: {}]", totalQty);
            throw new InvalidQuantityState("quantity.invalid");
        }

        if (price.compareTo(BigDecimal.ZERO) <= 0) {
            log.warn("Given product price is invalid [givenPrice: {}]", price);
            throw new InconsistentPrice("price.invalid");
        }
    }
}
