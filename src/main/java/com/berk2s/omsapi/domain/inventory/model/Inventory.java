package com.berk2s.omsapi.domain.inventory.model;

import com.berk2s.omsapi.domain.inventory.exception.OutOfQuantityException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.UUID;

import static com.berk2s.omsapi.domain.validation.InventoryValidator.validateProduct;
import static com.berk2s.omsapi.domain.validation.NullValidator.checkNonNull;

@Slf4j
@AllArgsConstructor
@Data
public class Inventory {

    private UUID productId;
    private String barcode;
    private String description;
    private Integer totalQuantity;
    private BigDecimal price;

    public static Inventory newInventory(UUID productId, String barcode, String description,
                                         Integer quantity, BigDecimal price) {
        var product = new Inventory(productId, barcode, description, quantity, price);
        product.validate();

        return product;
    }

    public void reserveQuantity(Integer reservedQuantity) {
        if (totalQuantity < reservedQuantity) {
            log.warn("Requested quantity is higher than inventory quantity [productId: {}]", productId);
            throw new OutOfQuantityException("quantity.outOfBounds");
        }

        totalQuantity -= reservedQuantity;
    }

    public void validate() {
        checkNonNull(productId, barcode, description, totalQuantity, price);

        validateProduct(totalQuantity, price);
    }
}
