package com.berk2s.omsapi.integrations.customer;

import com.berk2s.omsapi.IntegrationTestBase;
import com.berk2s.omsapi.infra.adapters.customer.controller.CustomerController;
import com.berk2s.omsapi.infra.adapters.customer.controller.dtos.CreateCustomerRequest;
import com.berk2s.omsapi.infra.adapters.customer.controller.dtos.UpdateOrderAddressRequest;
import com.berk2s.omsapi.infra.adapters.customer.controller.dtos.AddOrderLineRequest;
import com.berk2s.omsapi.infra.adapters.customer.controller.dtos.UpdateOrderLineRequest;
import com.berk2s.omsapi.infra.adapters.customer.entity.CustomerEntity;
import com.berk2s.omsapi.infra.adapters.inventory.entity.InventoryEntity;
import com.berk2s.omsapi.infra.adapters.order.entity.OrderEntity;
import com.berk2s.omsapi.infra.adapters.order.entity.OrderLineEntity;
import com.berk2s.omsapi.infra.exception.ErrorType;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.*;

class CustomerIT extends IntegrationTestBase {

    @DisplayName("Create Customer")
    @Nested
    class CreateCustomer {
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

    @DisplayName("Update Order Address")
    @Nested
    class UpdateOrderAddress {

        UpdateOrderAddressRequest request;
        CustomerEntity customer;
        OrderEntity order;

        @BeforeEach
        void setUp() {
            customer = createCustomer();
            order = createOrder(customer);

            request = UpdateOrderAddressRequest.builder()
                    .city("Istanbul")
                    .district("Besiktas")
                    .phoneNumber("5554442211")
                    .postalCode(34210)
                    .countryCode("TR")
                    .build();
        }

        @DisplayName("Update order address successfully")
        @Test
        void updateOrderAddressSuccessfully() throws Exception {
            mockMvc.perform(put(
                            CustomerController.ENDPOINT + "/" + customer.getId() + "/orders/" + order.getId() + "/address")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.orderId", is(order.getId().toString())))
                    .andExpect(jsonPath("$.customer.customerId", is(customer.getId().toString())))
                    .andExpect(jsonPath("$.address.countryCode", is(request.getCountryCode())))
                    .andExpect(jsonPath("$.address.city", is(request.getCity())))
                    .andExpect(jsonPath("$.address.district", is(request.getDistrict())))
                    .andExpect(jsonPath("$.address.postalCode", is(request.getPostalCode())))
                    .andExpect(jsonPath("$.address.phoneNumber", is(request.getPhoneNumber())));

        }

        @DisplayName("Invalid update address request error")
        @Test
        void invalidUpdateAddressRequestError() throws Exception {
            request.setCity(null);
            request.setDistrict(null);
            request.setCountryCode(null);
            request.setPhoneNumber(null);
            request.setPostalCode(null);

            mockMvc.perform(put(
                            CustomerController.ENDPOINT + "/" + customer.getId() + "/orders/" + order.getId() + "/address")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.error", is(ErrorType.INVALID_REQUEST.getType())))
                    .andExpect(jsonPath("$.error_description", is("request.invalid")))
                    .andExpect(jsonPath("$.details").isNotEmpty());

        }
    }

    @DisplayName("Add Order Line")
    @Nested
    class AddOrderLine {

        AddOrderLineRequest request;
        CustomerEntity customer;
        OrderEntity order;
        InventoryEntity inventory;

        @BeforeEach
        void setUp() {
            inventory = createInventoryEntity(null);
            customer = createCustomer();
            order = createOrder(customer);

            request = AddOrderLineRequest.builder()
                    .barcode(inventory.getBarcode())
                    .quantity(5)
                    .build();
        }

        @DisplayName("Add Product to existing Order")
        @Test
        void addProductToOrder() throws Exception {
            mockMvc.perform(post(CustomerController.ENDPOINT + "/" +
                            customer.getId() + "/orders/" + order.getId() + "/products")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.orderId").isNotEmpty())
                    .andExpect(jsonPath("$.customer.customerId", is(customer.getId().toString())))
                    .andExpect(jsonPath("$.customer.fullName", is(customer.getFullName())))
                    .andExpect(jsonPath("$.products..productId", anyOf(hasItem(is(inventory.getId().toString())))))
                    .andExpect(jsonPath("$.products..barcode", anyOf(hasItem(is(inventory.getBarcode())))))
                    .andExpect(jsonPath("$.products..description", anyOf(hasItem(is(inventory.getDescription())))))
                    .andExpect(jsonPath("$.products..quantity", anyOf(hasItem(is(request.getQuantity())))))
                    .andExpect(jsonPath("$.products..price", anyOf(hasItem(is(inventory.getPrice().doubleValue())))));
        }

        @DisplayName("Invalid add product request")
        @Test
        void invalidAddProductRequest() throws Exception {
            var request = AddOrderLineRequest.builder()
                    .quantity(null)
                    .barcode(null)
                    .build();

            mockMvc.perform(post(CustomerController.ENDPOINT + "/" +
                            UUID.randomUUID() + "/orders/" + UUID.randomUUID() + "/products")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.error", is(ErrorType.INVALID_REQUEST.getType())))
                    .andExpect(jsonPath("$.error_description", is("request.invalid")))
                    .andExpect(jsonPath("$.details").isNotEmpty());
        }
    }

    @DisplayName("Update Order Line")
    @Nested
    class UpdateOrderLine {

        UpdateOrderLineRequest request;
        CustomerEntity customer;
        OrderEntity order;
        OrderLineEntity orderLine;
        InventoryEntity inventory;

        @BeforeEach
        void setUp() {
            customer = createCustomer();
            order = createOrder(customer);
            orderLine = order.getOrderLines().get(0);
            inventory = createInventoryEntity(orderLine.getBarcode());

            request = UpdateOrderLineRequest.builder()
                    .quantity(7)
                    .build();
        }

        @DisplayName("Update Product Quantity")
        @Test
        void updateProductQuantity() throws Exception {
            mockMvc.perform(put(CustomerController.ENDPOINT + "/" +
                            customer.getId() + "/orders/" + order.getId() + "/products/" +
                            orderLine.getBarcode())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.orderId").isNotEmpty())
                    .andExpect(jsonPath("$.customer.customerId", is(customer.getId().toString())))
                    .andExpect(jsonPath("$.customer.fullName", is(customer.getFullName())))
                    .andExpect(jsonPath("$.products..productId", anyOf(hasItem(is(orderLine.getProduct().getId().toString())))))
                    .andExpect(jsonPath("$.products..barcode", anyOf(hasItem(is(inventory.getBarcode())))))
                    .andExpect(jsonPath("$.products..quantity", anyOf(hasItem(is(request.getQuantity())))))
                    .andExpect(jsonPath("$.products..price", anyOf(hasItem(is(orderLine.getProduct().getPrice().doubleValue())))));
        }

        @DisplayName("Invalid update product request")
        @Test
        void invalidUpdateProductRequest() throws Exception {
            var request = UpdateOrderLineRequest.builder()
                    .quantity(null)
                    .build();

            mockMvc.perform(put(CustomerController.ENDPOINT + "/" +
                            UUID.randomUUID() + "/orders/" + UUID.randomUUID() + "/products/" + RandomStringUtils.randomAlphabetic(10))
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.error", is(ErrorType.INVALID_REQUEST.getType())))
                    .andExpect(jsonPath("$.error_description", is("request.invalid")))
                    .andExpect(jsonPath("$.details").isNotEmpty());
        }
    }

    @DisplayName("Get Customer Orders")
    @Nested
    class CustomerOrders {

        CustomerEntity customer;
        OrderEntity order;

        @BeforeEach
        void setUp() {
            customer = createCustomer();
            order = createOrder(customer);
        }

        @DisplayName("Get Customer Orders successfully")
        @Test
        void getCustomerOrdersSuccessfully() throws Exception {
            mockMvc.perform(get(CustomerController.ENDPOINT + "/" + customer.getId() + "/orders"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$..orderId", anyOf(hasItem(is(order.getId().toString())))))
                    .andExpect(jsonPath("$..customer.customerId", anyOf(hasItem(is(customer.getId().toString())))))
                    .andExpect(jsonPath("$..customer.fullName", anyOf(hasItem(is(customer.getFullName())))));
        }
    }
}
