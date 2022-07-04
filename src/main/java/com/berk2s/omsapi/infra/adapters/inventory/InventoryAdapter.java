package com.berk2s.omsapi.infra.adapters.inventory;

import com.berk2s.omsapi.domain.inventory.model.Inventory;
import com.berk2s.omsapi.domain.inventory.port.InventoryPort;
import com.berk2s.omsapi.infra.adapters.inventory.entity.InventoryEntity;
import com.berk2s.omsapi.infra.adapters.inventory.facade.InventoryFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class InventoryAdapter implements InventoryPort {

    private final InventoryFacade inventoryFacade;

    @Override
    public Inventory retrieve(String barcode) {
        return inventoryFacade
                .findByBarcode(barcode)
                .toModel();
    }

    @Override
    public Inventory update(Inventory inventory) {
        var inventoryEntity = inventoryFacade.findByBarcode(inventory.getBarcode());
        inventoryEntity.setBarcode(inventory.getBarcode());
        inventoryEntity.setDescription(inventory.getDescription());
        inventoryEntity.setTotalQuantity(inventory.getTotalQuantity());
        inventoryEntity.setPrice(inventory.getPrice());

        return inventoryFacade.save(inventoryEntity).toModel();
    }

    @Override
    public Inventory save(Inventory inventory) {
        var inventoryEntity = new InventoryEntity();
        inventoryEntity.setBarcode(inventory.getBarcode());
        inventoryEntity.setDescription(inventory.getDescription());
        inventoryEntity.setPrice(inventory.getPrice());
        inventoryEntity.setTotalQuantity(inventory.getTotalQuantity());

        return inventoryFacade.save(inventoryEntity).toModel();
    }

    @Override
    public boolean existsByBarcode(String barcode) {
        return inventoryFacade.existsByBarcode(barcode);
    }
}
