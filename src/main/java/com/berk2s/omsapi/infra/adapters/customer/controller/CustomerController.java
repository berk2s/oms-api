package com.berk2s.omsapi.infra.adapters.customer.controller;

import com.berk2s.omsapi.domain.customer.model.Customer;
import com.berk2s.omsapi.domain.customer.usecase.CreateCustomer;
import com.berk2s.omsapi.domain.order.model.Order;
import com.berk2s.omsapi.domain.order.usecase.UpdateOrderAddress;
import com.berk2s.omsapi.domain.order.usecase.UpdateOrderLine;
import com.berk2s.omsapi.domain.usecase.UseCaseHandler;
import com.berk2s.omsapi.infra.adapters.customer.controller.dtos.*;
import com.berk2s.omsapi.infra.adapters.order.controller.dtos.OrderResponse;
import com.berk2s.omsapi.infra.adapters.order.entity.OrderEntity;
import com.berk2s.omsapi.infra.adapters.order.facade.OrderFacade;
import com.berk2s.omsapi.infra.exception.handler.dto.ErrorResponse;
import com.berk2s.omsapi.infra.sorting.SortingUtils;
import com.berk2s.omsapi.infra.swagger.SwaggerExample;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;
import java.util.stream.Collectors;

@Tag(name = "Customer", description = "Customer operations")
@RequiredArgsConstructor
@RequestMapping(CustomerController.ENDPOINT)
@RestController
public class CustomerController {

    public static final String ENDPOINT = "/customers";

    private final UseCaseHandler<Customer, CreateCustomer> createCustomerUseCaseHandler;
    private final UseCaseHandler<Order, UpdateOrderAddress> updateOrderAddressUseCaseHandler;
    private final UseCaseHandler<Order, UpdateOrderLine> updateOrderLineUseCaseHandler;
    private final OrderFacade orderFacade;

    @Operation(summary = "Create Customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Customer created", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerResponse.class),
                            examples = {
                                    @ExampleObject(name = "Successfully created Customer", value = SwaggerExample.CUSTOMER_CREATED_RESPONSE)
                            })
            }),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class),
                            examples = {
                                    @ExampleObject(name = "Bad request", value = SwaggerExample.CREATE_CUSTOMER_REQ_INVALID),
                                    @ExampleObject(name = "Range error",
                                            description = "Occurs when a full name is not in the size range",
                                            value = SwaggerExample.CREATE_CUSTOMER_FULL_NAME_NOT_IN_RAGE),
                            })
            })
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerResponse> createCustomer(@Valid @RequestBody CreateCustomerRequest request) {
        var customer = createCustomerUseCaseHandler.handle(request.toUseCase());

        return new ResponseEntity<>(CustomerResponse.from(customer), HttpStatus.CREATED);
    }

    @Operation(summary = "Add Product to Order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product added", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = OrderResponse.class),
                            examples = {
                                    @ExampleObject(name = "Successfully added product", value = SwaggerExample.ORDER_UPDATED_RESPONSE)
                            })
            }),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class),
                            examples = {
                                    @ExampleObject(name = "Bad request", value = SwaggerExample.ADD_ORDER_REQUEST_INVALID)
                            })
            }),
            @ApiResponse(responseCode = "404", description = "Not founds", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class),
                            examples = {
                                    @ExampleObject(name = "Customer not found", value = SwaggerExample.CUSTOMER_NOT_FOUND),
                                    @ExampleObject(name = "Order not found", value = SwaggerExample.ORDER_NOT_FOUND),
                                    @ExampleObject(name = "Product not found", value = SwaggerExample.PRODUCT_NOT_FOUND),
                            })
            })
    })
    @PostMapping(value = "/{customerId}/orders/{orderId}/products", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderResponse> addProductToOrder(@PathVariable UUID customerId,
                                                           @PathVariable UUID orderId,
                                                           @Valid @RequestBody AddOrderLineRequest request) {
        var order = updateOrderLineUseCaseHandler.handle(request.toUseCase(customerId, orderId));

        return new ResponseEntity<>(OrderResponse.from(order), HttpStatus.OK);
    }

    @Operation(summary = "Update Product from Order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product updated", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = OrderResponse.class),
                            examples = {
                                    @ExampleObject(name = "Successfully updated product", value = SwaggerExample.ORDER_UPDATED_RESPONSE)
                            })
            }),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class),
                            examples = {
                                    @ExampleObject(name = "Bad request", value = SwaggerExample.UPDATE_ORDER_REQUEST_INVALID)
                            })
            }),
            @ApiResponse(responseCode = "404", description = "Not founds", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class),
                            examples = {
                                    @ExampleObject(name = "Customer not found", value = SwaggerExample.CUSTOMER_NOT_FOUND),
                                    @ExampleObject(name = "Order not found", value = SwaggerExample.ORDER_NOT_FOUND),
                                    @ExampleObject(name = "Product not found", value = SwaggerExample.PRODUCT_NOT_FOUND),
                            })
            })
    })
    @PutMapping(value = "/{customerId}/orders/{orderId}/products/{barcode}", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderResponse> updateProductFromOrder(@PathVariable UUID customerId,
                                                                @PathVariable UUID orderId,
                                                                @PathVariable String barcode,
                                                                @Valid @RequestBody UpdateOrderLineRequest request) {
        var order = updateOrderLineUseCaseHandler.handle(request.toUseCase(customerId, orderId, barcode));

        return new ResponseEntity<>(OrderResponse.from(order), HttpStatus.OK);
    }

    @Operation(summary = "Update Delivery Address")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Address updated", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = OrderResponse.class),
                            examples = {
                                    @ExampleObject(name = "Successfully updated address", value = SwaggerExample.ORDER_UPDATED_RESPONSE)
                            })
            }),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class),
                            examples = {
                                    @ExampleObject(name = "Bad request", value = SwaggerExample.UPDATE_ORDER_ADDRESS_REQUEST_INVALID)
                            })
            }),
            @ApiResponse(responseCode = "404", description = "Not founds", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class),
                            examples = {
                                    @ExampleObject(name = "Customer not found", value = SwaggerExample.CUSTOMER_NOT_FOUND),
                                    @ExampleObject(name = "Order not found", value = SwaggerExample.ORDER_NOT_FOUND)
                            })
            })
    })
    @PutMapping(value = "/{customerId}/orders/{orderId}/address", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderResponse> updateOrderAddress(@PathVariable UUID customerId,
                                                            @PathVariable UUID orderId,
                                                            @Valid @RequestBody UpdateOrderAddressRequest request) {
        var order = updateOrderAddressUseCaseHandler.handle(request.toUseCase(customerId, orderId));

        return new ResponseEntity<>(OrderResponse.from(order), HttpStatus.OK);
    }

    @Operation(summary = "Customer Order List")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer Orders listed", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = OrderResponse.class),
                            examples = {
                                    @ExampleObject(name = "Successfully listed Customer Orders", value = SwaggerExample.LIST_CUSTOMER_ORDERS)
                            })
            }),
            @ApiResponse(responseCode = "404", description = "Not founds", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class),
                            examples = {
                                    @ExampleObject(name = "Customer not found", value = SwaggerExample.CUSTOMER_NOT_FOUND)
                    })
            })
    })
    @GetMapping(value = "/{customerId}/orders", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<OrderResponse>> getCustomerOrders(@PathVariable UUID customerId,
                                                                 @RequestParam(defaultValue = "0") Integer page,
                                                                 @RequestParam(defaultValue = "10") Integer size,
                                                                 @RequestParam(defaultValue = "createdAt") String sort,
                                                                 @RequestParam(defaultValue = "asc") String order) {
        var pageable = PageRequest.of(page, size, SortingUtils.generateSort(sort, order));

        var orders = orderFacade.findOrdersByCustomer(customerId, pageable);

        return new ResponseEntity<>(new PageImpl<>(
                OrderResponse.from(orders.getContent().stream().map(OrderEntity::toModel).collect(Collectors.toList())),
                pageable,
                orders.getTotalElements()), HttpStatus.OK);
    }
}
