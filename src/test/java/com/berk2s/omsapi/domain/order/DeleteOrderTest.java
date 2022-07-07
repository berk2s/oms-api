package com.berk2s.omsapi.domain.order;

import com.berk2s.omsapi.domain.mocks.OrderFakeAdapter;
import com.berk2s.omsapi.domain.order.exception.OrderNotFound;
import com.berk2s.omsapi.domain.order.model.Order;
import com.berk2s.omsapi.domain.order.usecase.DeleteOrder;
import com.berk2s.omsapi.domain.order.usecase.handler.DeleteOrderUseCaseHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DeleteOrderTest {

    DeleteOrderUseCaseHandler deleteOrderUseCaseHandler;

    @BeforeEach
    void setUp() {
        deleteOrderUseCaseHandler = new DeleteOrderUseCaseHandler(
                new OrderFakeAdapter());
    }

    @DisplayName("Should delete order successfully")
    @Test
    void shouldDeleteOrderSuccessfully() {
        // Given
        var deleteOrder = DeleteOrder.builder()
                .orderId(UUID.randomUUID())
                .build();

        // When
        var emptyOrder = deleteOrderUseCaseHandler
                .handle(deleteOrder);

        // Then
        assertThat(emptyOrder.getOrderId()).isNull();
    }

    @DisplayName("Should invalid order id returns error")
    @Test
    void shouldInvalidOrderIdReturnsError() {
        // Given
        var deleteOrder = DeleteOrder.builder()
                .orderId(null)
                .build();

        // When
        OrderNotFound exception = assertThrows(OrderNotFound.class,
                () -> deleteOrderUseCaseHandler.handle(deleteOrder));

        // Then
        assertThat(exception.getMessage()).isEqualTo("order.notFound");
    }
}
