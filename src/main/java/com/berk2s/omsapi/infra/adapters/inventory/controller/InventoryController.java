package com.berk2s.omsapi.infra.adapters.inventory.controller;

import com.berk2s.omsapi.domain.inventory.model.Inventory;
import com.berk2s.omsapi.domain.inventory.usecase.CreateInventory;
import com.berk2s.omsapi.infra.adapters.inventory.controller.dtos.CreateInventoryRequest;
import com.berk2s.omsapi.infra.adapters.inventory.controller.dtos.InventoryResponse;
import com.berk2s.omsapi.domain.usecase.UseCaseHandler;
import com.berk2s.omsapi.infra.exception.handler.dto.ErrorResponse;
import com.berk2s.omsapi.infra.swagger.SwaggerExample;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@RequestMapping(InventoryController.ENDPOINT)
@RestController
public class InventoryController {

    public static final String ENDPOINT = "/inventories";

    private final UseCaseHandler<Inventory, CreateInventory> createInventoryUseCaseHandler;

    @Operation(summary = "Create Inventory")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Inventory created", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = InventoryResponse.class),
                            examples = {
                                    @ExampleObject(name = "Successfully created Inventory", value = SwaggerExample.INVENTORY_CREATED_RESPONSE)
                            })
            }),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class),
                            examples = {
                                    @ExampleObject(name = "Bad request", value = SwaggerExample.CREATE_INVENTORY_REQ_INVALID),
                                    @ExampleObject(name = "Barcode taken error",
                                            description = "Occurs when a barcode belongs to a anotherInventory",
                                            value = SwaggerExample.INVENTORY_EXISTS_ERROR),
                            })
            })
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<InventoryResponse> createInventory(@Valid @RequestBody CreateInventoryRequest request) {
        var inventory = createInventoryUseCaseHandler
                .handle(request.toUseCase());

        return new ResponseEntity<>(InventoryResponse.from(inventory), HttpStatus.CREATED);
    }
}
