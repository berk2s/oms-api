package com.berk2s.omsapi.domain.order;

import com.berk2s.omsapi.domain.mocks.CustomerFakeAdapter;
import com.berk2s.omsapi.domain.mocks.OrderFakeAdapter;
import com.berk2s.omsapi.domain.mocks.ProductFakeAdapter;
import com.berk2s.omsapi.domain.order.usecase.CreateOrder;
import com.berk2s.omsapi.domain.order.usecase.handler.CreateOrderUseCaseHandler;
import com.berk2s.omsapi.domain.product.model.Product;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateOrderTest {

    private CreateOrderUseCaseHandler createOrderUseCaseHandler;

    @BeforeEach
    void setUp() {
        createOrderUseCaseHandler = new CreateOrderUseCaseHandler(new OrderFakeAdapter(),
                new CustomerFakeAdapter(),
                new ProductFakeAdapter());
    }

    @DisplayName("Should create order successfully")
    @Test
    void shouldCreateOrderSuccessfully() {
        // Given
        var createOrder = CreateOrder.builder()
                .customerId(UUID.randomUUID())
                .products(List.of(RandomStringUtils.randomAlphabetic(5)))
                .build();

        // When
        var order = createOrderUseCaseHandler
                .handle(createOrder);

        // Then
        assertThat(order).isNotNull()
                .returns(createOrder.getCustomerId(), i -> i.getCustomer().getCustomerId());

        assertThat(order.getProducts().stream().map(Product::getBarcode).collect(Collectors.toList()))
                .contains(createOrder.getProducts().get(0));
    }

}
