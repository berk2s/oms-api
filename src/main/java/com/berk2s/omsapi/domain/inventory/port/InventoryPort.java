package com.berk2s.omsapi.domain.inventory.port;

import com.berk2s.omsapi.domain.inventory.model.Inventory;

public interface InventoryPort {

    Inventory retrieve(String barcode);

    Inventory update(Inventory inventory);
}
