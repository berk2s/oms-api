package com.berk2s.omsapi.infra.adapters.inventory.controller;

import com.berk2s.omsapi.domain.inventory.model.Inventory;
import com.berk2s.omsapi.domain.inventory.usecase.CreateInventory;
import com.berk2s.omsapi.infra.adapters.inventory.controller.dtos.CreateInventoryRequest;
import com.berk2s.omsapi.infra.adapters.inventory.controller.dtos.InventoryResponse;
import com.berk2s.omsapi.domain.usecase.UseCaseHandler;
import com.berk2s.omsapi.infra.adapters.inventory.entity.InventoryEntity;
import com.berk2s.omsapi.infra.adapters.inventory.facade.InventoryFacade;
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
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.stream.Collectors;

@Tag(name = "Inventory", description = "Inventory operations")
@Slf4j
@RequiredArgsConstructor
@RequestMapping(InventoryController.ENDPOINT)
@RestController
public class InventoryController {

    public static final String ENDPOINT = "/inventories";

    private final UseCaseHandler<Inventory, CreateInventory> createInventoryUseCaseHandler;
    private final InventoryFacade inventoryFacade;

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

    @Operation(summary = "Inventory List")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inventories listed", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = InventoryResponse.class),
                            examples = {
                                    @ExampleObject(name = "Successfully listed Inventories", value = SwaggerExample.LIST_INVENTORIES)
                            })
            })
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<InventoryResponse>> getInventories(@RequestParam(defaultValue = "0") Integer page,
                                                                  @RequestParam(defaultValue = "10") Integer size,
                                                                  @RequestParam(defaultValue = "createdAt") String sort,
                                                                  @RequestParam(defaultValue = "asc") String order,
                                                                  @RequestParam(defaultValue = "") String search) {
        var pageable = PageRequest.of(page, size, SortingUtils.generateSort(sort, order));

        Page<InventoryEntity> inventories;
        if (StringUtils.isEmpty(search)) {
            inventories = inventoryFacade.listInventories(pageable);
        } else {
            inventories = inventoryFacade.listInventories(
                    pageable, search);
        }

        return new ResponseEntity<>(new PageImpl<>(
                InventoryResponse.from(inventories.getContent().stream().map(InventoryEntity::toModel).collect(Collectors.toList())),
                pageable,
                inventories.getTotalElements()), HttpStatus.OK);
    }
}
