package com.autoflex.application.product;

import com.autoflex.domain.Product;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class UpdateProductUseCase {

    private final IProductRepository repo;

    public UpdateProductUseCase(IProductRepository repo) {
        this.repo = repo;
    }

    @Transactional
    public Product execute(Long id, UpdateProductCommand cmd) {
        CreateProductUseCase.validate(cmd.code, cmd.name, cmd.price);

        Product p = repo.findById(id).orElseThrow(() -> new NotFoundException("Product not found"));

        // se trocar code, checar duplicidade
        String newCode = cmd.code.trim();
        if (!newCode.equals(p.code)) {
            repo.findByCode(newCode).ifPresent(existing -> {
                throw new IllegalArgumentException("Product code already exists");
            });
        }

        p.code = newCode;
        p.name = cmd.name.trim();
        p.price = cmd.price;

        // p já está managed dentro da transação -> update automático
        return p;
    }
}