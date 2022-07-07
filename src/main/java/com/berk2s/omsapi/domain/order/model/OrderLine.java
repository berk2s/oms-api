package com.berk2s.omsapi.domain.order.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

import static com.berk2s.omsapi.domain.validation.InventoryValidator.validateProduct;
import static com.berk2s.omsapi.domain.validation.NullValidator.checkNonNull;

@AllArgsConstructor
@Getter
@ToString
public class OrderLine {

    private UUID productId;
    private String barcode;
    private String description;
    private Integer quantity;
    private BigDecimal price;

    public static OrderLine newOrderLine(UUID productId, String barcode, String description,
                                     Integer quantity, BigDecimal price) {
        var product = new OrderLine(productId, barcode, description, quantity, price);
        product.validate();

        return product;
    }

    public void validate() {
        checkNonNull(productId, barcode, description, quantity, price);

        validateProduct(quantity, price);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderLine orderLine = (OrderLine) o;
        return Objects.equals(barcode, orderLine.barcode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(barcode);
    }
}
