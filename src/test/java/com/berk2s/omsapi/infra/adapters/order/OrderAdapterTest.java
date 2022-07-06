package com.berk2s.omsapi.infra.adapters.order;

import com.berk2s.omsapi.domain.order.model.Order;
import com.berk2s.omsapi.domain.order.model.OrderAddress;
import com.berk2s.omsapi.domain.order.model.OrderLine;
import com.berk2s.omsapi.domain.order.port.OrderPort;
import com.berk2s.omsapi.infra.adapters.customer.entity.CustomerEntity;
import com.berk2s.omsapi.infra.adapters.customer.facade.CustomerFacade;
import com.berk2s.omsapi.infra.adapters.inventory.facade.InventoryFacade;
import com.berk2s.omsapi.infra.adapters.order.entity.OrderLineEntity;
import com.berk2s.omsapi.infra.adapters.order.facade.OrderFacade;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static com.berk2s.omsapi.generators.MockGenerator.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderAdapterTest {

    @Mock
    OrderFacade orderFacade;

    @Mock
    CustomerFacade customerFacade;

    @Mock
    InventoryFacade inventoryFacade;

    @InjectMocks
    OrderAdapter orderAdapter;

    @DisplayName("Should retrieve order successfully")
    @Test
    void shouldRetrieveOrderSuccessfully() {
        // Given
        UUID orderId = UUID.randomUUID();
        when(orderFacade.findByOrderId(orderId)).thenReturn(mockOrderEntity(orderId));

        // When
        var order = orderAdapter.retrieve(orderId);

        // Then
        assertThat(order).isNotNull()
                .returns(orderId, Order::getOrderId);

        verify(orderFacade, times(1)).findByOrderId(any());
    }

    @DisplayName("Should create order successfully")
    @Test
    void shouldCreateOrderSuccessfully() {
        // Given
        UUID customerId = UUID.randomUUID();
        OrderLineEntity orderLineEntity = mockOrderLineEntity();
        CustomerEntity customerEntity = mockCustomerEntity(customerId);

        var orderToBeCreated = Order.newOrder(
                customerEntity.toModel(),
                mockOrderAddressEntity().toModel(),
                List.of(orderLineEntity.toModel()));

        when(customerFacade.findByCustomerId(any())).thenReturn(customerEntity);
        when(inventoryFacade.findByBarcode(any())).thenReturn(mockInventoryEntity(null));
        when(orderFacade.save(any())).thenAnswer(i -> i.getArguments()[0]);

        // When
        var order = orderAdapter.create(orderToBeCreated);

        // Then
        assertThat(order).isNotNull()
                .returns(customerId, i -> i.getCustomer().getCustomerId())
                .returns(1, i -> i.getProducts().size());

        verify(customerFacade, times(1)).findByCustomerId(any());
        verify(inventoryFacade, times(1)).findByBarcode(any());
        verify(orderFacade, times(1)).save(any());
    }

    @DisplayName("Should update order successfully")
    @Test
    void shouldUpdateOrderSuccessfully() {
        // Given
        UUID customerId = UUID.randomUUID();
        OrderLineEntity orderLineEntity = mockOrderLineEntity();
        CustomerEntity customerEntity = mockCustomerEntity(customerId);

        var orderToBeUpdated = Order.newOrder(
                UUID.randomUUID(),
                customerEntity.toModel(),
                mockOrderAddressEntity().toModel(),
                List.of(orderLineEntity.toModel()));

        when(customerFacade.findByCustomerId(any())).thenReturn(customerEntity);
        when(inventoryFacade.findByBarcode(any())).thenReturn(mockInventoryEntity(null));
        when(orderFacade.findByOrderId(any())).thenReturn(mockOrderEntity(null));
        when(orderFacade.save(any())).thenAnswer(i -> i.getArguments()[0]);

        // When
        var order = orderAdapter.update(orderToBeUpdated);

        // Then
        assertThat(order).isNotNull()
                .returns(customerId, i -> i.getCustomer().getCustomerId())
                .returns(2, i -> i.getProducts().size());

        verify(customerFacade, times(1)).findByCustomerId(any());
        verify(inventoryFacade, times(1)).findByBarcode(any());
        verify(orderFacade, times(1)).save(any());
        verify(orderFacade, times(1)).findByOrderId(any());
    }

}