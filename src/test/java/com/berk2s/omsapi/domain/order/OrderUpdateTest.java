package com.berk2s.omsapi.domain.order;

import com.berk2s.omsapi.domain.mocks.CustomerFakeAdapter;
import com.berk2s.omsapi.domain.mocks.InventoryFakeAdapter;
import com.berk2s.omsapi.domain.mocks.OrderFakeAdapter;
import com.berk2s.omsapi.domain.order.model.OrderAddress;
import com.berk2s.omsapi.domain.order.model.OrderLine;
import com.berk2s.omsapi.domain.order.usecase.handler.CreateOrderUseCaseHandler;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static com.berk2s.omsapi.domain.mocks.MockGenerator.mockOrder;
import static com.berk2s.omsapi.domain.mocks.MockGenerator.mockOrderLine;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderUpdateTest {

    private CreateOrderUseCaseHandler createOrderUseCaseHandler;

    @BeforeEach
    void setUp() {
        createOrderUseCaseHandler = new CreateOrderUseCaseHandler(new OrderFakeAdapter(),
                new CustomerFakeAdapter(),
                new InventoryFakeAdapter());
    }

    @DisplayName("Should add product to Order successfully")
    @Test
    void shouldAddProductToOrderSuccessfully() {
        // Given
        var order = mockOrder(10, 20, 30, 40);

        var calculatedPriceSnapshot = order.totalPrice();

        // When
        var orderLineToBeAdded = mockOrderLine(5, BigDecimal.valueOf(50));
        order.addProduct(orderLineToBeAdded);

        // Then
        assertEquals(calculatedPriceSnapshot.add(orderLineToBeAdded.getPrice()), order.totalPrice());
    }

    @DisplayName("Should remove product to Order successfully")
    @Test
    void shouldRemoveProductToOrderSuccessfully() {
        // Given
        var order = mockOrder(10, 20, 30, 40);

        var calculatedPriceSnapshot = order.totalPrice();
        var productToBeDeleted = order.getProducts().get(0);

        // When
        order.removeProduct(productToBeDeleted);

        // Then
        assertEquals(calculatedPriceSnapshot.subtract(productToBeDeleted.getPrice()), order.totalPrice());
    }

    @DisplayName("Should update product to Order successfully")
    @Test
    void shouldUpdateProductToOrderSuccessfully() {
        // Given
        var order = mockOrder(10, 20, 30, 40);

        var calculatedPriceSnapshot = order.totalPrice();
        var productToBeUpdated = order.getProducts().get(0);

        var updatedOrderLine = OrderLine
                .newOrderLine(productToBeUpdated.getProductId(),
                        RandomStringUtils.randomAlphabetic(4),
                        RandomStringUtils.randomAlphabetic(4),
                        5,
                        BigDecimal.valueOf(100));


        // When
        order.updateProduct(updatedOrderLine);

        // Then
        var finalPrice = calculatedPriceSnapshot
                .subtract(productToBeUpdated.getPrice())
                .add(updatedOrderLine.getPrice());

        assertEquals(finalPrice, order.totalPrice());
    }

    @DisplayName("Should update order address successfully")
    @Test
    void shouldUpdateOrderAddressSuccessfully() {
        // Given
        var order = mockOrder(10, 20, 30, 40);

        var newAddress = OrderAddress.newAddress("TR",
                "Izmir", "Karsiyaka", 35122, "5552221133");


        // When
        order.updateAddress(newAddress);

        // Then
        assertEquals(newAddress, order.getAddress());
    }
}
