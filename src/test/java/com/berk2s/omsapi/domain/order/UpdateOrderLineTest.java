package com.berk2s.omsapi.domain.order;

import com.berk2s.omsapi.domain.inventory.exception.OutOfQuantityException;
import com.berk2s.omsapi.domain.inventory.port.InventoryPort;
import com.berk2s.omsapi.domain.mocks.InventoryFakeAdapter;
import com.berk2s.omsapi.domain.mocks.OrderFakeAdapter;
import com.berk2s.omsapi.domain.order.exception.ProductNotFound;
import com.berk2s.omsapi.domain.order.model.OrderLine;
import com.berk2s.omsapi.domain.order.port.OrderPort;
import com.berk2s.omsapi.domain.order.usecase.UpdateOrderLine;
import com.berk2s.omsapi.domain.order.usecase.handler.UpdateOrderLineUseCaseHandler;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UpdateOrderLineTest {

    private UpdateOrderLineUseCaseHandler updateOrderLineUseCaseHandler;

    OrderPort orderPort;
    InventoryPort inventoryPort;

    @BeforeEach
    void setUp() {
        orderPort = new OrderFakeAdapter();
        inventoryPort = new InventoryFakeAdapter();

        updateOrderLineUseCaseHandler = new UpdateOrderLineUseCaseHandler(
                orderPort, inventoryPort);
    }

    @DisplayName("Should update order line successfully")
    @Test
    void shouldUpdateOrderLineSuccessfully() {
        // Given
        var orderId = UUID.randomUUID();

        var retrievedInventory = inventoryPort
                .retrieve(RandomStringUtils.randomAlphabetic(5));

        var updateOrderLine = UpdateOrderLine.builder()
                .orderId(orderId)
                .customerId(UUID.randomUUID())
                .barcode(retrievedInventory.getBarcode())
                .quantity(10)
                .build();

        // When
        var order = updateOrderLineUseCaseHandler.handle(updateOrderLine);

        // Then
        assertThat(order).isNotNull();

        assertThat(order.getOrderId())
                .isEqualTo(updateOrderLine.getOrderId());

        assertThat(order.getProducts().stream().filter(i -> i.getBarcode().equals(retrievedInventory.getBarcode())).findFirst().get())
                .returns(updateOrderLine.getQuantity(), OrderLine::getQuantity);
    }

    @DisplayName("Should return error when requested quantity is invalid")
    @Test
    void shouldReturnErrorWhenRequestedQuantityIsInvalid() {
        // Given
        var updateOrderLine = UpdateOrderLine.builder()
                .orderId(UUID.randomUUID())
                .customerId(UUID.randomUUID())
                .barcode(RandomStringUtils.randomAlphabetic(10))
                .quantity(100000000)
                .build();

        // When
        OutOfQuantityException exception = assertThrows(OutOfQuantityException.class,
                () -> updateOrderLineUseCaseHandler.handle(updateOrderLine));

        // Then
        assertThat(exception.getMessage()).isEqualTo("quantity.outOfBounds");
    }
}