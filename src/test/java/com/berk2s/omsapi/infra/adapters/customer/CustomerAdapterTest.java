package com.berk2s.omsapi.infra.adapters.customer;

import com.berk2s.omsapi.domain.customer.model.Customer;
import com.berk2s.omsapi.infra.adapters.customer.facade.CustomerFacade;
import com.berk2s.omsapi.infra.exception.EntityNotFound;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static com.berk2s.omsapi.generators.MockGenerator.mockCustomerEntity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerAdapterTest {

    @Mock
    CustomerFacade customerFacade;

    @InjectMocks
    CustomerAdapter customerAdapter;

    @DisplayName("Should retrieve customer successfully")
    @Test
    void shouldRetrieveCustomerSuccessfully() {
        // Given
        UUID customerId = UUID.randomUUID();
        when(customerFacade.findByCustomerId(customerId)).thenReturn(mockCustomerEntity(customerId));


        // When
        var customer = customerAdapter
                .retrieve(customerId);

        // Then
        assertThat(customer).isNotNull()
                .returns(customerId, Customer::getCustomerId);

        verify(customerFacade, times(1)).findByCustomerId(any());
    }

    @DisplayName("Should throw error when customer not found")
    @Test
    void shouldThrowExceptionWhenCustomerNotFound() {
        // Given
        UUID customerId = UUID.randomUUID();
        when(customerFacade.findByCustomerId(customerId)).thenThrow(new EntityNotFound("customer.notFound"));


        // When
        EntityNotFound exception = assertThrows(EntityNotFound.class,
                () -> customerAdapter.retrieve(customerId));

        // Then
        assertEquals("customer.notFound", exception.getMessage());
        verify(customerFacade, times(1)).findByCustomerId(any());
    }

    @DisplayName("Should create customer successfully")
    @Test
    void shouldCreateCustomerSuccessfully() {
        // Given
        var customerToBeCreated = Customer.newCustomer(RandomStringUtils.randomAlphabetic(5));

        when(customerFacade.save(any())).thenReturn(mockCustomerEntity(null));

        // When
        var customer = customerAdapter
                .create(customerToBeCreated);

        // Then
        assertThat(customer).isNotNull();

        verify(customerFacade, times(1)).save(any());
    }

}