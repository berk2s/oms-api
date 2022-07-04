package com.berk2s.omsapi.domain.order;

import com.berk2s.omsapi.domain.mocks.OrderFakeAdapter;
import com.berk2s.omsapi.domain.order.usecase.UpdateOrderAddress;
import com.berk2s.omsapi.domain.order.usecase.handler.UpdateOrderAddressUseCaseHandler;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class UpdateOrderAddressTest {

    UpdateOrderAddressUseCaseHandler updateOrderAddressUseCaseHandler;

    @BeforeEach
    void setUp() {
        updateOrderAddressUseCaseHandler = new UpdateOrderAddressUseCaseHandler(
                new OrderFakeAdapter()
        );
    }

    @DisplayName("Should update order address successfully")
    @Test
    void shouldUpdateOrderAddressSuccessfully() {
        // Given
        var updateAddress = UpdateOrderAddress.builder()
                .orderId(UUID.randomUUID())
                .city(RandomStringUtils.randomAlphabetic(5))
                .district(RandomStringUtils.randomAlphabetic(5))
                .phoneNumber(RandomStringUtils.randomAlphabetic(11))
                .postalCode(35123)
                .countryCode(RandomStringUtils.randomAlphabetic(2))
                .build();

        // When
        var order = updateOrderAddressUseCaseHandler
                .handle(updateAddress);

        // Then
        assertThat(order).isNotNull();

        assertThat(order.getAddress().getCity()).isEqualTo(updateAddress.getCity());
        assertThat(order.getAddress().getDistrict()).isEqualTo(updateAddress.getDistrict());
        assertThat(order.getAddress().getPhoneNumber()).isEqualTo(updateAddress.getPhoneNumber());
        assertThat(order.getAddress().getPostalCode()).isEqualTo(updateAddress.getPostalCode());
        assertThat(order.getAddress().getCountryCode()).isEqualTo(updateAddress.getCountryCode());
    }

}
