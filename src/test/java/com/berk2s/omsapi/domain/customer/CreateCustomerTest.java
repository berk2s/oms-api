package com.berk2s.omsapi.domain.customer;

import com.berk2s.omsapi.domain.customer.model.Customer;
import com.berk2s.omsapi.domain.customer.usecase.CreateCustomer;
import com.berk2s.omsapi.domain.customer.usecase.handler.CreateCustomerUseCaseHandler;
import com.berk2s.omsapi.domain.mocks.CustomerFakeAdapter;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateCustomerTest {

    private CreateCustomerUseCaseHandler createCustomerUseCaseHandler;

    @BeforeEach
    void setUp() {
        createCustomerUseCaseHandler
                = new CreateCustomerUseCaseHandler(new CustomerFakeAdapter());
    }

    @DisplayName("Should create customer successfully")
    @Test
    void shouldCreateCustomerSuccessfully() {
        // Given
        var createCustomer = CreateCustomer.builder()
                .fullName(RandomStringUtils.randomAlphabetic(6))
                .build();

        // When
        var customer = createCustomerUseCaseHandler
                .handle(createCustomer);

        // Then
        assertThat(customer).isNotNull()
                .returns(createCustomer.getFullName(), Customer::getFullName);
    }

}
