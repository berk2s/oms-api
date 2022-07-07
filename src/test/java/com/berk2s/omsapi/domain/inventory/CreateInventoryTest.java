package com.berk2s.omsapi.domain.inventory;

import com.berk2s.omsapi.domain.inventory.exception.InventoryExists;
import com.berk2s.omsapi.domain.inventory.model.Inventory;
import com.berk2s.omsapi.domain.inventory.usecase.CreateInventory;
import com.berk2s.omsapi.domain.inventory.usecase.handler.CreateInventoryUseCaseHandler;
import com.berk2s.omsapi.domain.mocks.InventoryFakeAdapter;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CreateInventoryTest {

    CreateInventoryUseCaseHandler createInventoryUseCaseHandler;

    @BeforeEach
    void setUp() {
        createInventoryUseCaseHandler = new CreateInventoryUseCaseHandler(
                new InventoryFakeAdapter()
        );
    }

    @DisplayName("Should create inventory successfully")
    @Test
    void shouldCreateInventorySuccessfully() {
        // Given
        var createInventory = CreateInventory.builder()
                .barcode(RandomStringUtils.randomAlphabetic(10))
                .description(RandomStringUtils.randomAlphabetic(10))
                .price(BigDecimal.valueOf(10))
                .totalQuantity(100)
                .build();

        // When
        var inventory = createInventoryUseCaseHandler
                .handle(createInventory);

        // Then
        assertThat(inventory).isNotNull()
                .returns(createInventory.getTotalQuantity(), Inventory::getTotalQuantity)
                .returns(createInventory.getPrice(), Inventory::getPrice)
                .returns(createInventory.getBarcode(), Inventory::getBarcode)
                .returns(createInventory.getDescription(), Inventory::getDescription);
    }

    @DisplayName("Should return exception when barcode taken")
    @Test
    void shouldReturnExceptionWhenBarcodeTkane() {
        // Given
        var createInventory = CreateInventory.builder()
                .barcode(null)
                .description(RandomStringUtils.randomAlphabetic(10))
                .price(BigDecimal.valueOf(10))
                .totalQuantity(100)
                .build();

        // When
        InventoryExists exception = assertThrows(InventoryExists.class,
                () -> createInventoryUseCaseHandler
                        .handle(createInventory));

        // Then
        assertEquals("inventory.exists", exception.getMessage());
    }


}
