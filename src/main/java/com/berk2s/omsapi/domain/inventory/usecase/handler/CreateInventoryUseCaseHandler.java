package com.berk2s.omsapi.domain.inventory.usecase.handler;

import com.berk2s.omsapi.domain.annotations.DomainService;
import com.berk2s.omsapi.domain.inventory.exception.InventoryExists;
import com.berk2s.omsapi.domain.inventory.model.Inventory;
import com.berk2s.omsapi.domain.inventory.port.InventoryPort;
import com.berk2s.omsapi.domain.inventory.usecase.CreateInventory;
import com.berk2s.omsapi.domain.usecase.UseCaseHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@DomainService
public class CreateInventoryUseCaseHandler implements UseCaseHandler<Inventory, CreateInventory> {

    private final InventoryPort inventoryPort;

    /**
     * Creates inventory
     */
    @Override
    public Inventory handle(CreateInventory createInventory) {
        if (inventoryPort.existsByBarcode(createInventory.getBarcode())) {
            log.warn("Inventory with given barcode already exists [barcode: {}]", createInventory.getBarcode());
            throw new InventoryExists("inventory.exists");
        }

        var inventory = Inventory.newInventory(null,
                createInventory.getBarcode(),
                createInventory.getDescription(),
                createInventory.getTotalQuantity(),
                createInventory.getPrice());

        return inventoryPort.save(inventory);
    }
}
