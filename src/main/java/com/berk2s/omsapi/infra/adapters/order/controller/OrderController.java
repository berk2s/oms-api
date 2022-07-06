package com.berk2s.omsapi.infra.adapters.order.controller;

import com.berk2s.omsapi.domain.order.model.Order;
import com.berk2s.omsapi.domain.order.usecase.CreateOrder;
import com.berk2s.omsapi.domain.usecase.UseCaseHandler;
import com.berk2s.omsapi.infra.adapters.order.controller.dtos.CreateOrderRequest;
import com.berk2s.omsapi.infra.adapters.order.controller.dtos.OrderResponse;
import com.berk2s.omsapi.infra.exception.handler.dto.ErrorResponse;
import com.berk2s.omsapi.infra.swagger.SwaggerExample;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Create Order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Order created", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = OrderResponse.class),
                            examples = {
                                    @ExampleObject(name = "Successfully created Order", value = SwaggerExample.ORDER_CREATED_RESPONSE)
                            })
            }),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class),
                            examples = {
                                    @ExampleObject(name = "Bad request", value = SwaggerExample.CREATE_ORDER_REQ_INVALID),
                                    @ExampleObject(name = "Out of bounds quantity",
                                            description = "Occurs when requested quantity is higher than inventory quantity",
                                            value = SwaggerExample.CREATE_ORDER_OUT_OF_BOUNDS_ERROR),
                            })
            }),
            @ApiResponse(responseCode = "404", description = "Not founds", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class),
                            examples = {
                                    @ExampleObject(name = "Customer not found", value = SwaggerExample.CUSTOMER_NOT_FOUND),
                                    @ExampleObject(name = "Product not found", value = SwaggerExample.PRODUCT_NOT_FOUND),
                            })
            })
    })
    @PostMapping(value = "/{customerId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderResponse> createOrder(@PathVariable UUID customerId,
                                                     @Valid @RequestBody CreateOrderRequest req) {
        var order = createOrderUseCaseHandler
                .handle(req.toUseCase(customerId));

        return new ResponseEntity<>(OrderResponse.from(order), HttpStatus.CREATED);
    }

}
