package com.autoflex.api.product;

import com.autoflex.domain.ProductRawMaterial;

public class ProductRawMaterialItemResponse {
    public Long rawMaterialId;
    public String rawMaterialCode;
    public String rawMaterialName;
    public Long requiredQuantity;

    public static ProductRawMaterialItemResponse of(ProductRawMaterial prm){
        ProductRawMaterialItemResponse response = new ProductRawMaterialItemResponse();
        response.rawMaterialId = prm.rawMaterial.id;
        response.rawMaterialCode = prm.rawMaterial.code;
        response.rawMaterialName = prm.rawMaterial.name;
        response.requiredQuantity = prm.rawMaterial.stockQuantity;
        return response;
    }
}
