package com.autoflex.infrastructure.persistence.product;

import com.autoflex.application.product.IProductRepository;
import com.autoflex.domain.Product;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class ProductRepositoryAdapter implements IProductRepository {

    private final ProductPanacheRepository repo;

    public ProductRepositoryAdapter(ProductPanacheRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<Product> findAll() {
        return repo.listAll();
    }

    @Override
    public Optional<Product> findById(Long id) {
        return Optional.ofNullable(repo.findById(id));
    }

    @Override
    public Optional<Product> findByCode(String code) {
        return repo.find("code", code).firstResultOptional();
    }

    @Override
    @Transactional
    public void save(Product product) {
        // se id null -> persist; se não -> já está managed quando veio do findById
        if (product.id == null) repo.persist(product);
    }

    @Override
    @Transactional
    public boolean deleteById(Long id) {
        return repo.deleteById(id);
    }
}