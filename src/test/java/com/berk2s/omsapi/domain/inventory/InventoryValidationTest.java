package com.berk2s.omsapi.domain.inventory;

import com.berk2s.omsapi.domain.inventory.model.Inventory;
import com.berk2s.omsapi.domain.order.exception.InconsistentPrice;
import com.berk2s.omsapi.domain.order.exception.InvalidQuantityState;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class InventoryValidationTest {

    @DisplayName("Should throw exception When given quantity is invalid")
    @Test
    void shouldThrowException_whenGivenQuantityIsInvalid() {

        InvalidQuantityState exception = assertThrows(InvalidQuantityState.class,
                () -> Inventory.newInventory(UUID.randomUUID(),
                        RandomStringUtils.randomAlphabetic(5),
                        RandomStringUtils.randomAlphabetic(5),
                        -1,
                        BigDecimal.valueOf(10)));

        assertEquals("quantity.invalid", exception.getMessage());
    }

    @DisplayName("Should throw exception When given price is invalid")
    @Test
    void shouldThrowException_whenGivenPriceIsInvalid() {

        InconsistentPrice exception = assertThrows(InconsistentPrice.class,
                () -> Inventory.newInventory(UUID.randomUUID(),
                        RandomStringUtils.randomAlphabetic(5),
                        RandomStringUtils.randomAlphabetic(5),
                        10,
                        BigDecimal.valueOf(-1)));

        assertEquals("price.invalid", exception.getMessage());
    }
}
