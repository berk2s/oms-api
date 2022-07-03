package com.berk2s.omsapi.domain.address;

import com.berk2s.omsapi.domain.customer.exception.FakePhoneNumber;
import com.berk2s.omsapi.domain.customer.exception.InvalidCountryCode;
import com.berk2s.omsapi.domain.customer.exception.InvalidPostalCode;
import com.berk2s.omsapi.domain.customer.model.Address;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AddressValidationTest {

    @DisplayName("Should throw exception When invalid country code")
    @Test
    void shouldThrowExceptionWhenInvalidCountryCode() {

        // When
        InvalidCountryCode exception = assertThrows(InvalidCountryCode.class,
                () -> Address.newAddress("TURKEY", "Izmir", "Konak", 35290,  "5552221100"));

        // Then
        assertEquals("countryCode.invalid", exception.getMessage());
    }

    @DisplayName("Should throw exception When invalid postal code")
    @Test
    void shouldThrowExceptionWhenInvalidPostalCode() {

        // When
        InvalidPostalCode exception = assertThrows(InvalidPostalCode.class,
                () -> Address.newAddress("TR", "Izmir", "Konak", 35,  "5552221100"));

        // Then
        assertEquals("postalCode.invalid", exception.getMessage());
    }

    @DisplayName("Should throw exception When invalid phone number")
    @Test
    void shouldThrowExceptionWhenInvalidPhoneNumber() {

        // When
        FakePhoneNumber exception = assertThrows(FakePhoneNumber.class,
                () -> Address.newAddress("TR", "Izmir", "Konak", 35290,  "1234"));

        // Then
        assertEquals("phoneNumber.invalid", exception.getMessage());
    }
}
