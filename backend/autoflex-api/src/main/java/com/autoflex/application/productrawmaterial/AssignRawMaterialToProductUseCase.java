package com.autoflex.application.productrawmaterial;

import com.autoflex.application.product.IProductRepository;
import com.autoflex.application.rawmaterial.IRawMaterialRepository;
import com.autoflex.domain.Product;
import com.autoflex.domain.ProductRawMaterial;
import com.autoflex.domain.ProductRawMaterialId;
import com.autoflex.domain.RawMaterial;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class AssignRawMaterialToProductUseCase {

    private final IProductRepository productRepo;
    private final IRawMaterialRepository rawMaterialRepo;
    private final IProductRawMaterialRepository productRawMaterialRepo;

    public AssignRawMaterialToProductUseCase(
            IProductRepository productRepo,
            IRawMaterialRepository rawMaterialRepo,
            IProductRawMaterialRepository productRawMaterialRepo
    ) {
        this.productRepo = productRepo;
        this.rawMaterialRepo = rawMaterialRepo;
        this.productRawMaterialRepo = productRawMaterialRepo;
    }

    @Transactional
    public ProductRawMaterial execute(AssignRawMaterialToProductCommand cmd) {
        if (cmd == null) throw new IllegalArgumentException("Command is required");
        if (cmd.productId == null) throw new IllegalArgumentException("productId is required");
        if (cmd.rawMaterialId == null) throw new IllegalArgumentException("rawMaterialId is required");
        if (cmd.requiredQuantity == null) throw new IllegalArgumentException("requiredQuantity is required");
        if (cmd.requiredQuantity <= 0) throw new IllegalArgumentException("requiredQuantity must be > 0");

        Product product = productRepo.findById(cmd.productId)
                .orElseThrow(() -> new NotFoundException("Product not found"));

        RawMaterial rawMaterial = rawMaterialRepo.findById(cmd.rawMaterialId)
                .orElseThrow(() -> new NotFoundException("Raw material not found"));

        ProductRawMaterial association = productRawMaterialRepo
                .findByIds(cmd.productId, cmd.rawMaterialId)
                .orElseGet(() -> {
                    ProductRawMaterial prm = new ProductRawMaterial();
                    prm.id = new ProductRawMaterialId(cmd.productId, cmd.rawMaterialId);
                    prm.product = product;
                    prm.rawMaterial = rawMaterial;
                    return prm;
                });

        association.requiredQuantity = cmd.requiredQuantity;
        productRawMaterialRepo.save(association);

        return association;
    }
}