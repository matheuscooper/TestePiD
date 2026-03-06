package com.autoflex.infrastructure.persistence.product;

import com.autoflex.domain.Product;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ProductPanacheRepository implements PanacheRepository<Product> {}