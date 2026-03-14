package com.autoflex.application.product;

import com.autoflex.domain.Product;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class GetProductUseCase {
    private final IProductRepository repo;

    public GetProductUseCase(IProductRepository repo) {
        this.repo = repo;
    }

    public Product execute(Long id) {
        return repo.findById(id).orElseThrow(() -> new NotFoundException("Product not found"));
    }
}