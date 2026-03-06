package com.autoflex.application.product;

import com.autoflex.domain.Product;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class CreateProductUseCase {

    private final IProductRepository productRepo;

    public CreateProductUseCase(IProductRepository productRepo) {
        this.productRepo = productRepo;
    }

    @Transactional
    public Product execute(CreateProductCommand cmd) {

        
        
        productRepo.findByCode(cmd.code).ifPresent(p -> {
            throw new IllegalArgumentException("Product code already exists");
        });
        
        Product p = new Product();
        p.code = cmd.code;
        p.name = cmd.name;
        p.price = cmd.price;
        
        productRepo.save(p);
        return p;
        
    }
    static void validate(String code, String name, java.math.BigDecimal price) {

        if (code == null || code.isBlank()) throw new IllegalArgumentException("code is required");
        if (name == null || name.isBlank()) throw new IllegalArgumentException("name is required");
        if (price == null) throw new IllegalArgumentException("price is required");
        if (price.signum() < 0) throw new IllegalArgumentException("price must be >= 0");
    }
}