package com.berk2s.omsapi.domain.customer;

import com.berk2s.omsapi.domain.customer.exception.FakeName;
import com.berk2s.omsapi.domain.customer.model.Customer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CustomerValidationTest {

    @DisplayName("Should throw exception When given full name is invalid")
    @Test
    void shouldThrowExceptionWhenGivenFullNameIsInvalid() {

        // When
        FakeName exception = assertThrows(FakeName.class,
                () -> Customer.newCustomer("a"));

        // Then
        assertEquals("fullName.invalid", exception.getMessage());
    }

}
