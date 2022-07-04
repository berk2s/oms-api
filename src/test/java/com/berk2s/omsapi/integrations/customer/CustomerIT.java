package com.berk2s.omsapi.integrations.customer;

import com.berk2s.omsapi.IntegrationTestBase;
import com.berk2s.omsapi.infra.adapters.customer.controller.CustomerController;
import com.berk2s.omsapi.infra.adapters.customer.controller.dtos.CreateCustomerRequest;
import com.berk2s.omsapi.infra.exception.ErrorType;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.*;

class CustomerIT extends IntegrationTestBase {

    CreateCustomerRequest createCustomerRequest;

    @BeforeEach
    void setUp() {
        createCustomerRequest = CreateCustomerRequest.builder()
                .fullName(RandomStringUtils.randomAlphabetic(5))
                .build();
    }

    @DisplayName("Create customer successfully")
    @Test
    void createCustomerSuccessfully() throws Exception {

        mockMvc.perform(post(CustomerController.ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(createCustomerRequest)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.customerId").isNotEmpty())
                .andExpect(jsonPath("$.fullName", is(createCustomerRequest.getFullName())))
                .andExpect(jsonPath("$.createdAt").isNotEmpty())
                .andExpect(jsonPath("$.lastModifiedAt").isNotEmpty());
    }

    @DisplayName("Invalid create customer request body returns error")
    @Test
    void invalidCreateCustomerRequestBodyReturnsError() throws Exception {
        createCustomerRequest.setFullName(null);

        mockMvc.perform(post(CustomerController.ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(createCustomerRequest)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", is(ErrorType.INVALID_REQUEST.getType())))
                .andExpect(jsonPath("$.error_description", is("request.invalid")))
                .andExpect(jsonPath("$.details").isNotEmpty());
    }

    @DisplayName("Invalid create customer full name not in range returns error")
    @Test
    void invalidCreateCustomerFullNameNotInRangeReturnsError() throws Exception {
        createCustomerRequest.setFullName(RandomStringUtils.randomAlphabetic(200));

        mockMvc.perform(post(CustomerController.ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(createCustomerRequest)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", is(ErrorType.INVALID_REQUEST.getType())))
                .andExpect(jsonPath("$.error_description", is("request.invalid")))
                .andExpect(jsonPath("$.details").isNotEmpty());
    }
}
