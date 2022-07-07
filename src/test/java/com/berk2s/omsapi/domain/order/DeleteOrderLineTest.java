package com.berk2s.omsapi.domain.order;

import com.berk2s.omsapi.domain.mocks.InventoryFakeAdapter;
import com.berk2s.omsapi.domain.mocks.OrderFakeAdapter;
import com.berk2s.omsapi.domain.order.port.OrderPort;
import com.berk2s.omsapi.domain.order.usecase.DeleteOrderLine;
import com.berk2s.omsapi.domain.order.usecase.handler.DeleteOrderLineUseCaseHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeleteOrderLineTest {

    DeleteOrderLineUseCaseHandler deleteOrderLineUseCaseHandler;
    OrderPort orderPort;

    @BeforeEach
    void setUp() {
        orderPort = new OrderFakeAdapter();
        deleteOrderLineUseCaseHandler = new DeleteOrderLineUseCaseHandler(
                orderPort,
                new InventoryFakeAdapter());
    }

    @DisplayName("Delete order line successfully")
    @Test
    void deleteOrderLineSuccessfully() {
        // Given
        var orderId = UUID.randomUUID();

        var deleteOrderLine = DeleteOrderLine.builder()
                .orderId(orderId)
                .barcode("barcode")
                .build();

        // When
        var order = deleteOrderLineUseCaseHandler.handle(deleteOrderLine);

        // Then
        assertThat(order).isNotNull();

        assertEquals(0, order.getProducts().size());
    }
}
