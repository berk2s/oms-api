package com.berk2s.omsapi.domain.product.model;

import com.berk2s.omsapi.domain.product.exception.InconsistentPrice;
import com.berk2s.omsapi.domain.product.exception.InvalidQuantityState;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

import static com.berk2s.omsapi.domain.validation.NullValidator.checkNonNull;

@AllArgsConstructor
@Data
public class Product {

    private UUID productId;
    private String barcode;
    private String description;
    private Integer quantity;
    private BigDecimal price;

    public static Product newProduct(UUID productId, String barcode, String description,
                                 Integer quantity, BigDecimal price) {
        var product = new Product(productId, barcode, description, quantity, price);
        product.validate();

        return product;
    }

    public void validate() {
        checkNonNull(productId, barcode, description, quantity, price);

        if (quantity <= 0) {
            throw new InvalidQuantityState("Quantity must be greater than zero");
        }

        if (price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InconsistentPrice("Order price must be greater than zero");
        }
    }
}
