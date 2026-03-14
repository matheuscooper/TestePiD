package com.autoflex.infrastructure.persistence.productrawmaterial;

import com.autoflex.domain.ProductRawMaterial;
import com.autoflex.domain.ProductRawMaterialId;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ProductRawMaterialPanacheRepository implements PanacheRepositoryBase<ProductRawMaterial, ProductRawMaterialId>{   
}
