package com.berk2s.omsapi.infra.adapters.order.controller;

import com.berk2s.omsapi.domain.order.model.Order;
import com.berk2s.omsapi.domain.order.usecase.CreateOrder;
import com.berk2s.omsapi.domain.usecase.UseCaseHandler;
import com.berk2s.omsapi.infra.adapters.order.controller.dtos.CreateOrderRequest;
import com.berk2s.omsapi.infra.adapters.order.controller.dtos.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping(OrderController.ENDPOINT)
@RestController
public class OrderController {

    public static final String ENDPOINT = "/orders";

    private final UseCaseHandler<Order, CreateOrder> createOrderUseCaseHandler;

    @PostMapping(value = "/{customerId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderResponse> createOrder(@PathVariable UUID customerId,
                                                     @Valid @RequestBody CreateOrderRequest req) {
        var order = createOrderUseCaseHandler
                .handle(req.toUseCase(customerId));

        return new ResponseEntity<>(OrderResponse.from(order), HttpStatus.CREATED);
    }

}
