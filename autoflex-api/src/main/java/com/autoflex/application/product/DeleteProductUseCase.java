package com.autoflex.application.product;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class DeleteProductUseCase {

    private final IProductRepository repo;

    public DeleteProductUseCase(IProductRepository repo) {
        this.repo = repo;
    }

    @Transactional
    public void execute(Long id) {
        boolean deleted = repo.deleteById(id);
        if (!deleted) throw new NotFoundException("Product not found");
    }
}