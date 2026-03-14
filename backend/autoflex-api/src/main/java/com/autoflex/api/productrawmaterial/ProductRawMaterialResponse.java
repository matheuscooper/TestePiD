package com.autoflex.api.productrawmaterial;

import com.autoflex.domain.ProductRawMaterial;

public class ProductRawMaterialResponse {
    public Long productId;
    public Long rawMaterialId;
    public Long requiredQuantity;

    public static ProductRawMaterialResponse of(ProductRawMaterial prm){
        ProductRawMaterialResponse response = new ProductRawMaterialResponse(); 
        response.productId = prm.product.id;
        response.rawMaterialId = prm.rawMaterial.id;
        response.requiredQuantity = prm.requiredQuantity;
        return response;
    }
}
