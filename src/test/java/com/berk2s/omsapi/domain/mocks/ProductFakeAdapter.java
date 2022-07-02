package com.berk2s.omsapi.domain.mocks;

import com.berk2s.omsapi.domain.product.model.Product;
import com.berk2s.omsapi.domain.product.port.ProductPort;
import org.apache.commons.lang3.RandomStringUtils;

import java.math.BigDecimal;
import java.util.UUID;

public class ProductFakeAdapter implements ProductPort {
    @Override
    public Product retrieve(String barcode) {
        return Product.newProduct(UUID.randomUUID(),
                barcode,
                RandomStringUtils.randomAlphabetic(8),
                1,
                BigDecimal.valueOf(35.0));
    }
}
