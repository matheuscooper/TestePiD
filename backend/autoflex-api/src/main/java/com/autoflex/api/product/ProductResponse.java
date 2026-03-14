package com.autoflex.api.product;

import java.math.BigDecimal;
import java.util.List;


public class ProductResponse {
    public Long id;
    public String code;
    public String name;
    public BigDecimal price;
    public List<ProductRawMaterialItemResponse> rawMaterials;

    public static ProductResponse of(com.autoflex.domain.Product p) {
        ProductResponse response = new ProductResponse();
        response.id = p.id;
        response.code = p.code;
        response.name = p.name;
        response.price = p.price;
        response.rawMaterials = p.rawMaterials.stream().map(ProductRawMaterialItemResponse::of).toList();
        return response;
    }
}