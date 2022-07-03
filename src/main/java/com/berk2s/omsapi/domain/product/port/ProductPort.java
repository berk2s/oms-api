package com.berk2s.omsapi.domain.product.port;

import com.berk2s.omsapi.domain.product.model.Product;

public interface ProductPort {

    Product retrieve(String barcode);
}
