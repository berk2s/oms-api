package com.berk2s.omsapi.domain.mocks;

import com.berk2s.omsapi.domain.inventory.model.Inventory;
import com.berk2s.omsapi.domain.inventory.port.InventoryPort;
import org.apache.commons.lang3.RandomStringUtils;

import java.math.BigDecimal;
import java.util.UUID;

public class InventoryFakeAdapter implements InventoryPort {
    @Override
    public Inventory retrieve(String barcode) {
        return Inventory.newInventory(UUID.randomUUID(),
                barcode,
                RandomStringUtils.randomAlphabetic(8),
                99,
                BigDecimal.valueOf(35.0));
    }

    @Override
    public Inventory update(Inventory inventory) {
        return inventory;
    }

}
