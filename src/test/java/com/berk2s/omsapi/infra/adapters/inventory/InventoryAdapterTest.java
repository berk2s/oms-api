package com.berk2s.omsapi.infra.adapters.inventory;

import com.berk2s.omsapi.domain.inventory.model.Inventory;
import com.berk2s.omsapi.infra.adapters.inventory.facade.InventoryFacade;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.UUID;

import static com.berk2s.omsapi.generators.MockGenerator.mockInventoryEntity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InventoryAdapterTest {

    @Mock
    InventoryFacade inventoryFacade;

    @InjectMocks
    InventoryAdapter inventoryAdapter;

    @DisplayName("Should create inventory successfully")
    @Test
    void shouldCreateInventorySuccessfully() {
        // Given
        var inventoryToBeSaved = Inventory.builder()
                .barcode(RandomStringUtils.randomAlphabetic(10))
                .description(RandomStringUtils.randomAlphabetic(10))
                .totalQuantity(10)
                .price(BigDecimal.valueOf(5))
                .build();

        when(inventoryFacade.save(any())).thenReturn(mockInventoryEntity(null));

        // When
        var inventory = inventoryAdapter
                .save(inventoryToBeSaved);

        // Then
        assertThat(inventory).isNotNull();

        verify(inventoryFacade, times(1)).save(any());
    }

    @DisplayName("Should update inventory successfully")
    @Test
    void shouldUpdateInventorySuccessfully() {
        // Given
        var inventoryToBeUpdated = Inventory.builder()
                .inventoryId(UUID.randomUUID())
                .barcode(RandomStringUtils.randomAlphabetic(10))
                .description(RandomStringUtils.randomAlphabetic(10))
                .totalQuantity(10)
                .price(BigDecimal.valueOf(5))
                .build();

        when(inventoryFacade.findByBarcode(any())).thenReturn(mockInventoryEntity(inventoryToBeUpdated.getInventoryId()));
        when(inventoryFacade.save(any())).thenReturn(mockInventoryEntity(inventoryToBeUpdated.getInventoryId()));

        // When
        var inventory = inventoryAdapter
                .update(inventoryToBeUpdated);

        // Then
        assertThat(inventory).isNotNull();

        verify(inventoryFacade, times(1)).findByBarcode(any());
        verify(inventoryFacade, times(1)).save(any());
    }

    @DisplayName("Should retrieve inventory successfully")
    @Test
    void shouldRetrieveInventorySuccessfully() {
        // Given
        String barcode = RandomStringUtils.randomAlphabetic(10);

        when(inventoryFacade.findByBarcode(any())).thenReturn(mockInventoryEntity(null));

        // When
        var inventory = inventoryAdapter
                .retrieve(barcode);

        // Then
        assertThat(inventory).isNotNull();

        verify(inventoryFacade, times(1)).findByBarcode(any());
    }

}