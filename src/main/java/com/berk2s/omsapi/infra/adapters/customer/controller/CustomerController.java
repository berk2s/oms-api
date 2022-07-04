package com.berk2s.omsapi.infra.adapters.customer.controller;

import com.berk2s.omsapi.domain.customer.model.Customer;
import com.berk2s.omsapi.domain.customer.usecase.CreateCustomer;
import com.berk2s.omsapi.domain.usecase.UseCaseHandler;
import com.berk2s.omsapi.infra.adapters.customer.controller.dtos.CreateCustomerRequest;
import com.berk2s.omsapi.infra.adapters.customer.controller.dtos.CustomerResponse;
import com.berk2s.omsapi.infra.exception.handler.dto.ErrorResponse;
import com.berk2s.omsapi.infra.swagger.SwaggerExample;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Tag(name = "Customer", description = "Customer operations")
@RequiredArgsConstructor
@RequestMapping(CustomerController.ENDPOINT)
@RestController
public class CustomerController {

    public static final String ENDPOINT = "/customers";

    private final UseCaseHandler<Customer, CreateCustomer> createCustomerUseCaseHandler;

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
}
