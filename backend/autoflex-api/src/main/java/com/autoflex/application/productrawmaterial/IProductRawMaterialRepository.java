package com.autoflex.application.productrawmaterial;

import java.util.Optional;

import com.autoflex.domain.ProductRawMaterial;

public interface IProductRawMaterialRepository {
    Optional<ProductRawMaterial> findByIds(Long productId, Long rawMaterialId); 
    void save(ProductRawMaterial productRawMaterial);
} 
