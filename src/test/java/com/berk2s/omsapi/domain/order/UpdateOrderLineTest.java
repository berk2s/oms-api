package com.berk2s.omsapi.domain.order;

import com.berk2s.omsapi.domain.mocks.OrderFakeAdapter;
import com.berk2s.omsapi.domain.order.exception.ProductNotFound;
import com.berk2s.omsapi.domain.order.model.Order;
import com.berk2s.omsapi.domain.order.model.OrderLine;
import com.berk2s.omsapi.domain.order.usecase.UpdateOrderLine;
import com.berk2s.omsapi.domain.order.usecase.handler.UpdateOrderLineUseCaseHandler;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UpdateOrderLineTest {

    private UpdateOrderLineUseCaseHandler updateOrderLineUseCaseHandler;

    @BeforeEach
    void setUp() {
        updateOrderLineUseCaseHandler = new UpdateOrderLineUseCaseHandler(new OrderFakeAdapter());
    }

    @DisplayName("Should update order line successfully")
    @Test
    void shouldUpdateOrderLineSuccessfully() {
        // Given
        var orderId = UUID.randomUUID();

        var updateOrderLine = UpdateOrderLine.builder()
                .orderId(orderId)
                .productId(orderId)
                .barcode(RandomStringUtils.randomAlphabetic(10))
                .description(RandomStringUtils.randomAlphabetic(10))
                .price(BigDecimal.valueOf(5))
                .quantity(10)
                .build();

        // When
        var order = updateOrderLineUseCaseHandler.handle(updateOrderLine);

        // Then
        assertThat(order).isNotNull();

        assertThat(order.getOrderId())
                .isEqualTo(updateOrderLine.getOrderId());

        assertThat(order.getProducts().stream().filter(i -> i.getProductId().equals(orderId)).findFirst().get())
                .returns(updateOrderLine.getBarcode(), OrderLine::getBarcode)
                .returns(updateOrderLine.getDescription(), OrderLine::getDescription)
                .returns(updateOrderLine.getPrice(), OrderLine::getPrice)
                .returns(updateOrderLine.getQuantity(), OrderLine::getQuantity)
                .returns(updateOrderLine.getProductId(), OrderLine::getProductId);
    }

    @DisplayName("Should return error when invalid product id is given")
    @Test
    void shouldReturnErrorWhenInvalidProductIdGiven() {
        // Given
        var updateOrderLine = UpdateOrderLine.builder()
                .orderId(UUID.randomUUID())
                .productId(UUID.randomUUID())
                .barcode(RandomStringUtils.randomAlphabetic(10))
                .description(RandomStringUtils.randomAlphabetic(10))
                .price(BigDecimal.valueOf(5))
                .quantity(10)
                .build();

        // When
        ProductNotFound exception = assertThrows(ProductNotFound.class,
                () -> updateOrderLineUseCaseHandler.handle(updateOrderLine));

        // Then
        assertThat(exception.getMessage()).isEqualTo("product.notFound");
    }
}