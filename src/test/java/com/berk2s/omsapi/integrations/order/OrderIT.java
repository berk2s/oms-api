package com.berk2s.omsapi.integrations.order;

import com.berk2s.omsapi.IntegrationTestBase;
import com.berk2s.omsapi.infra.adapters.customer.entity.CustomerEntity;
import com.berk2s.omsapi.infra.adapters.inventory.entity.InventoryEntity;
import com.berk2s.omsapi.infra.adapters.order.controller.OrderController;
import com.berk2s.omsapi.infra.adapters.order.controller.dtos.CreateOrderRequest;
import com.berk2s.omsapi.infra.adapters.order.entity.OrderEntity;
import com.berk2s.omsapi.infra.exception.ErrorType;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class OrderIT extends IntegrationTestBase {

    String barcode;
    CustomerEntity customer;
    InventoryEntity inventory;
    CreateOrderRequest.DeliveryAddress deliveryAddress;

    CreateOrderRequest createOrderReq;

    @BeforeEach
    void setUp() {
        barcode = RandomStringUtils.randomAlphabetic(8);
        customer = createCustomer();
        inventory = createInventoryEntity(barcode);
        deliveryAddress = createDeliveryAddress();
    }

    @DisplayName("Create Order")
    @Nested
    class CreateOrder {

        @BeforeEach
        void setUp() {
            createOrderReq = CreateOrderRequest.builder()
                    .deliveryAddress(deliveryAddress)
                    .products(List.of(createOrderProduct(inventory.getBarcode(), 1)))
                    .build();
        }

        @DisplayName("Create order successfully")
        @Test
        void createOrderSuccessfully() throws Exception {

            mockMvc.perform(post(OrderController.ENDPOINT + "/" + customer.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(createOrderReq)))
                    .andDo(print())
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.orderId").isNotEmpty())
                    .andExpect(jsonPath("$.customer.customerId", is(customer.getId().toString())))
                    .andExpect(jsonPath("$.customer.fullName", is(customer.getFullName())))
                    .andExpect(jsonPath("$.address.countryCode", is(deliveryAddress.getCountryCode())))
                    .andExpect(jsonPath("$.address.city", is(deliveryAddress.getCity())))
                    .andExpect(jsonPath("$.address.district", is(deliveryAddress.getDistrict())))
                    .andExpect(jsonPath("$.address.postalCode", is(deliveryAddress.getPostalCode())))
                    .andExpect(jsonPath("$.address.phoneNumber", is(deliveryAddress.getPhoneNumber())))
                    .andExpect(jsonPath("$.products..productId", anyOf(hasItem(is(inventory.getId().toString())))))
                    .andExpect(jsonPath("$.products..barcode", anyOf(hasItem(is(inventory.getBarcode())))))
                    .andExpect(jsonPath("$.products..description", anyOf(hasItem(is(inventory.getDescription())))))
                    .andExpect(jsonPath("$.products..quantity", anyOf(hasItem(is(createOrderReq.getProducts().get(0).getRequestedQty())))))
                    .andExpect(jsonPath("$.products..price", anyOf(hasItem(is(inventory.getPrice().doubleValue())))));
        }

        @DisplayName("Invalid request")
        @Test
        void invalidRequest() throws Exception {
            mockMvc.perform(post(OrderController.ENDPOINT + "/" + customer.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(CreateOrderRequest.builder().build())))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.error", is(ErrorType.INVALID_REQUEST.getType())))
                    .andExpect(jsonPath("$.error_description", is("request.invalid")))
                    .andExpect(jsonPath("$.details").isNotEmpty());
        }

        @DisplayName("Customer not found error")
        @Test
        void customerNotFoundError() throws Exception {
            mockMvc.perform(post(OrderController.ENDPOINT + "/" + UUID.randomUUID())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(createOrderReq)))
                    .andDo(print())
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.error", is(ErrorType.NOT_FOUND.getType())))
                    .andExpect(jsonPath("$.error_description", is("customer.notFound")));
        }

        @DisplayName("Product not found error")
        @Test
        void productNotFoundError() throws Exception {
            createOrderReq.setProducts(List.of(createOrderProduct(RandomStringUtils.randomAlphabetic(10), 100)));

            mockMvc.perform(post(OrderController.ENDPOINT + "/" + customer.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(createOrderReq)))
                    .andDo(print())
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.error", is(ErrorType.NOT_FOUND.getType())))
                    .andExpect(jsonPath("$.error_description", is("inventory.notFound")));
        }

        @DisplayName("Requested quantity is out of inventory quantity error")
        @Test
        void requestedQuantityIsOutOfInventoryQuantityError() throws Exception {
            createOrderReq.setProducts(List.of(createOrderProduct(inventory.getBarcode(), 10000)));
            mockMvc.perform(post(OrderController.ENDPOINT + "/" + customer.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(createOrderReq)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.error", is(ErrorType.INVALID_REQUEST.getType())))
                    .andExpect(jsonPath("$.error_description", is("quantity.outOfBounds")));
        }
    }

    @DisplayName("Delete Order")
    @Nested
    class DeleteOrder {

        OrderEntity orderEntity;

        @BeforeEach
        void setUp() {
            orderEntity = createOrder(createCustomer());
        }

        @DisplayName("Delete order successfully")
        @Test
        void deleteOrderSuccessfully() throws Exception {
            mockMvc.perform(delete(OrderController.ENDPOINT + "/" + orderEntity.getId()))
                    .andDo(print())
                    .andExpect(status().isNoContent());
        }
    }
    private CreateOrderRequest.DeliveryAddress createDeliveryAddress() {
        return CreateOrderRequest.DeliveryAddress.builder()
                .countryCode(RandomStringUtils.randomAlphabetic(2))
                .city(RandomStringUtils.randomAlphabetic(2))
                .district(RandomStringUtils.randomAlphabetic(2))
                .phoneNumber(RandomStringUtils.randomAlphabetic(10))
                .postalCode(35125)
                .build();
    }

    private CreateOrderRequest.OrderProduct createOrderProduct(String barcode, Integer qty) {
        return CreateOrderRequest.OrderProduct.builder()
                .barcode(barcode)
                .requestedQty(qty)
                .build();
    }
}
