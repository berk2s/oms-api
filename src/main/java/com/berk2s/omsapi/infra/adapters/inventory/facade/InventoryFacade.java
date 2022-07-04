package com.berk2s.omsapi.infra.adapters.inventory.facade;

import com.berk2s.omsapi.infra.adapters.inventory.entity.InventoryEntity;
import com.berk2s.omsapi.infra.adapters.inventory.repository.InventoryRepository;
import com.berk2s.omsapi.infra.exception.EntityNotFound;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class InventoryFacade {

    private final InventoryRepository inventoryRepository;

    public InventoryEntity save(InventoryEntity inventory) {
        return inventoryRepository.save(inventory);
    }

    public boolean existsByBarcode(String barcode) {
        return inventoryRepository
                .existsByBarcode(barcode);
    }

    public InventoryEntity findByBarcode(String barcode) {
        return inventoryRepository.findByBarcode(barcode)
                .orElseThrow(() -> {
                    log.warn("Inventory with given barcode does not exists [barcode: {}]", barcode);
                    throw new EntityNotFound("inventory.notFound");
                });
    }

}
