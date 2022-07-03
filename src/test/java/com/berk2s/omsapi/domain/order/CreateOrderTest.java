package com.berk2s.omsapi.domain.order;

import com.berk2s.omsapi.domain.inventory.exception.OutOfQuantityException;
import com.berk2s.omsapi.domain.mocks.CustomerFakeAdapter;
import com.berk2s.omsapi.domain.mocks.OrderFakeAdapter;
import com.berk2s.omsapi.domain.mocks.InventoryFakeAdapter;
import com.berk2s.omsapi.domain.order.model.OrderLine;
import com.berk2s.omsapi.domain.order.usecase.CreateOrder;
import com.berk2s.omsapi.domain.order.usecase.handler.CreateOrderUseCaseHandler;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class CreateOrderTest {

    private CreateOrderUseCaseHandler createOrderUseCaseHandler;

    @BeforeEach
    void setUp() {
        createOrderUseCaseHandler = new CreateOrderUseCaseHandler(new OrderFakeAdapter(),
                new CustomerFakeAdapter(),
                new InventoryFakeAdapter());
    }

    @DisplayName("Should create order successfully")
    @Test
    void shouldCreateOrderSuccessfully() {
        // Given
        var createOrder = CreateOrder.builder()
                .customerId(UUID.randomUUID())
                .products(List.of(createOrderProduct(RandomStringUtils.randomAlphabetic(5), 5)))
                .deliveryAddress(createDeliveryAddress())
                .build();

        // When
        var order = createOrderUseCaseHandler
                .handle(createOrder);

        // Then
        assertThat(order).isNotNull()
                .returns(createOrder.getCustomerId(), i -> i.getCustomer().getCustomerId());

        assertThat(order.getProducts().stream().map(OrderLine::getBarcode).collect(Collectors.toList()))
                .contains(createOrder.getProducts().get(0).getBarcode());
    }

    @DisplayName("Should throw exception when requested quantity is higher than inventory quantity")
    @Test
    void shouldThrowException_whenRequestedQuantityIsHigherThanInventoryQty() {
        // Given
        var createOrder = CreateOrder.builder()
                .customerId(UUID.randomUUID())
                .products(List.of(createOrderProduct(RandomStringUtils.randomAlphabetic(5), 10000)))
                .deliveryAddress(createDeliveryAddress())
                .build();

        // When
        OutOfQuantityException exception = assertThrows(OutOfQuantityException.class,
                () -> createOrderUseCaseHandler.handle(createOrder));

        // Then

        assertEquals("quantity.outOfBounds", exception.getMessage());
    }

    @DisplayName("Should calculate total price successfully")
    @Test
    void shouldCalculateTotalPriceSuccessfully() {
        // Given
        var createOrder = CreateOrder.builder()
                .customerId(UUID.randomUUID())
                .products(List.of(
                        createOrderProduct(RandomStringUtils.randomAlphabetic(5), 5),
                        createOrderProduct(RandomStringUtils.randomAlphabetic(5), 5)
                        ))
                .deliveryAddress(createDeliveryAddress())
                .build();

        // When
        var order = createOrderUseCaseHandler
                .handle(createOrder);

        // Then
        assertEquals(order.totalPrice(), BigDecimal.valueOf(70.0));
    }

    private CreateOrder.OrderProduct createOrderProduct(String barcode, Integer qty) {
        return CreateOrder.OrderProduct.builder()
                .barcode(barcode)
                .requestedQty(qty)
                .build();
    }

    private CreateOrder.DeliveryAddress createDeliveryAddress() {
        return CreateOrder.DeliveryAddress.builder()
                .countryCode("TR")
                .city("Izmir")
                .district("Konak")
                .postalCode(35290)
                .phoneNumber("5552221133")
                .build();
    }
}
