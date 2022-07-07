package com.berk2s.omsapi.integrations.inventory;

import com.berk2s.omsapi.IntegrationTestBase;
import com.berk2s.omsapi.infra.adapters.inventory.controller.InventoryController;
import com.berk2s.omsapi.infra.adapters.inventory.controller.dtos.CreateInventoryRequest;
import com.berk2s.omsapi.infra.exception.ErrorType;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class InventoryIT extends IntegrationTestBase {

    CreateInventoryRequest req;

    @BeforeEach
    void setUp() {
        req = CreateInventoryRequest.builder()
                .barcode(RandomStringUtils.randomAlphabetic(10))
                .description(RandomStringUtils.randomAlphabetic(10))
                .price(BigDecimal.valueOf(10))
                .totalQuantity(10)
                .build();
    }

    @DisplayName("Create Inventory Successfully")
    @Test
    void createInventorySuccessfully() throws Exception {
        mockMvc.perform(post(InventoryController.ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andDo(print())
                .andExpect(jsonPath("$.inventoryId").isNotEmpty())
                .andExpect(jsonPath("$.barcode", is(req.getBarcode())))
                .andExpect(jsonPath("$.description", is(req.getDescription())))
                .andExpect(jsonPath("$.totalQuantity", is(req.getTotalQuantity())))
                .andExpect(jsonPath("$.price", is(req.getPrice().intValue())))
                .andExpect(jsonPath("$.createdAt").isNotEmpty())
                .andExpect(jsonPath("$.lastModifiedAt").isNotEmpty());

    }

    @DisplayName("Create Inventory Invalid Request Body Error")
    @Test
    void createInventoryInvalidRequestBodyError() throws Exception {
        req.setBarcode(null);
        req.setDescription(null);
        req.setPrice(null);
        req.setTotalQuantity(null);

        mockMvc.perform(post(InventoryController.ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", is(ErrorType.INVALID_REQUEST.getType())))
                .andExpect(jsonPath("$.error_description", is("request.invalid")))
                .andExpect(jsonPath("$.details").isNotEmpty());

    }


    @DisplayName("Barcode Taken Error")
    @Test
    void barcodeTakenError() throws Exception {
        var createdInventory = createInventoryEntity(null);

        req.setBarcode(createdInventory.getBarcode());

        mockMvc.perform(post(InventoryController.ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", is(ErrorType.INVALID_REQUEST.getType())))
                .andExpect(jsonPath("$.error_description", is("inventory.exists")));

    }

    @DisplayName("List Inventory Successfully")
    @Test
    void listInventorySuccessfully() throws Exception {
        var inventory = createInventoryEntity(null);

        mockMvc.perform(get(InventoryController.ENDPOINT))
                .andDo(print())
                .andExpect(jsonPath("$..inventoryId").isNotEmpty())
                .andExpect(jsonPath("$..barcode", anyOf(hasItem(is(inventory.getBarcode())))))
                .andExpect(jsonPath("$..description", anyOf(hasItem(is(inventory.getDescription())))))
                .andExpect(jsonPath("$..totalQuantity", anyOf(hasItem(is(inventory.getTotalQuantity())))))
                .andExpect(jsonPath("$..price", anyOf(hasItem(is(inventory.getPrice().doubleValue())))))
                .andExpect(jsonPath("$..createdAt").isNotEmpty())
                .andExpect(jsonPath("$..lastModifiedAt").isNotEmpty());

    }
}
