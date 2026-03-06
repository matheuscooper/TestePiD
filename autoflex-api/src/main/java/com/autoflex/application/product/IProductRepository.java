package com.autoflex.application.product;

import com.autoflex.domain.Product;
import java.util.List;
import java.util.Optional;

public interface IProductRepository {
    List<Product> findAll();
    Optional<Product> findById(Long id);
    Optional<Product> findByCode(String code);

    void save(Product product);      
    boolean deleteById(Long id);
}