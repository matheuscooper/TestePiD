package com.autoflex.application.product;

import com.autoflex.domain.Product;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class ListProductsUseCase {
    private final IProductRepository repo;

    public ListProductsUseCase(IProductRepository repo) {
        this.repo = repo;
    }

    public List<Product> execute() {
        return repo.findAll();
    }
}