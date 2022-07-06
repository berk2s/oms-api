package com.berk2s.omsapi.infra.adapters.order.controller.dtos;

import com.berk2s.omsapi.domain.customer.model.Customer;
import com.berk2s.omsapi.domain.order.model.Order;
import com.berk2s.omsapi.domain.order.model.OrderAddress;
import com.berk2s.omsapi.domain.order.model.OrderLine;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@Builder
public class OrderResponse {

    private UUID orderId;
    private CustomerResponse customer;
    private List<OrderLineResponse> products;
    private OrderAddressResponse address;
    private BigDecimal totalPrice;

    public static OrderResponse from(Order order) {
        return OrderResponse.builder()
                .orderId(order.getOrderId())
                .customer(CustomerResponse.from(order.getCustomer()))
                .products(order.getProducts().stream().map(OrderLineResponse::from).collect(Collectors.toList()))
                .address(OrderAddressResponse.from(order.getAddress()))
                .totalPrice(order.totalPrice())
                .build();
    }

    @Getter
    @Builder
    public static class OrderLineResponse {
        private UUID productId;
        private String barcode;
        private String description;
        private Integer quantity;
        private BigDecimal price;

        public static OrderLineResponse from(OrderLine orderLine) {
            return OrderLineResponse.builder()
                    .productId(orderLine.getProductId())
                    .barcode(orderLine.getBarcode())
                    .description(orderLine.getDescription())
                    .quantity(orderLine.getQuantity())
                    .price(orderLine.getPrice())
                    .build();
        }
    }

    @Getter
    @Builder
    public static class OrderAddressResponse {
        private String countryCode;
        private String city;
        private String district;
        private Integer postalCode;
        private String phoneNumber;

        public static OrderAddressResponse from(OrderAddress orderAddress) {
            return OrderAddressResponse.builder()
                    .countryCode(orderAddress.getCountryCode())
                    .city(orderAddress.getCity())
                    .district(orderAddress.getDistrict())
                    .postalCode(orderAddress.getPostalCode())
                    .phoneNumber(orderAddress.getPhoneNumber())
                    .build();
        }
    }

    @Getter
    @Builder
    public static class CustomerResponse {
        private UUID customerId;
        private String fullName;

        public static CustomerResponse from(Customer customer) {
            return CustomerResponse.builder()
                    .customerId(customer.getCustomerId())
                    .fullName(customer.getFullName())
                    .build();
        }
    }
}
