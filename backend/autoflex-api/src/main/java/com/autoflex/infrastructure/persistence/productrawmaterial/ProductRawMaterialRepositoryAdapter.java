package com.autoflex.infrastructure.persistence.productrawmaterial;

import java.util.Optional;

import com.autoflex.application.productrawmaterial.IProductRawMaterialRepository;
import com.autoflex.domain.ProductRawMaterial;
import com.autoflex.domain.ProductRawMaterialId;

import jakarta.enterprise.context.ApplicationScoped;


@ApplicationScoped
public class ProductRawMaterialRepositoryAdapter implements IProductRawMaterialRepository {
    private final ProductRawMaterialPanacheRepository repo;

    public ProductRawMaterialRepositoryAdapter( ProductRawMaterialPanacheRepository repo){
        this.repo = repo;
    }

    @Override
    public Optional<ProductRawMaterial> findByIds( Long productId, Long rawMaterialId){
        ProductRawMaterialId id = new ProductRawMaterialId(productId, rawMaterialId);
        return Optional.ofNullable(repo.findById(id));
    }


    @Override
    public void save( ProductRawMaterial productRawMaterial){
        if(repo.findById(productRawMaterial.id)== null) {
            repo.persist(productRawMaterial);
        }
    }
}
