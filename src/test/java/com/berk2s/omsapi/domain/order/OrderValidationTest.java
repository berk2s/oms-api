package com.berk2s.omsapi.domain.order;

import com.berk2s.omsapi.domain.order.exception.EmptyProductState;
import com.berk2s.omsapi.domain.order.model.Order;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.berk2s.omsapi.domain.mocks.MockGenerator.mockAddress;
import static com.berk2s.omsapi.domain.mocks.MockGenerator.mockCustomer;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class OrderValidationTest {

    @DisplayName("Should throw error When product list empty")
    @Test
    void shouldThrowErrorWhenProductListEmpty() {
        // When
        EmptyProductState exception = assertThrows(EmptyProductState.class,
                () -> Order.newOrder(mockCustomer(null), mockAddress(), List.of()));

        // Then
        assertEquals("orderLine.empty", exception.getMessage());
    }

}
