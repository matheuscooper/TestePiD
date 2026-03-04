package com.autoflex.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(
    name=  "product",
    uniqueConstraints = @UniqueConstraint(name = "uk_product_code", columnNames = "code")

) public class Product {

     @Id
     @GeneratedValue( strategy = GenerationType.IDENTITY )
     public Long id;

     @Column(nullable = false, length = 50)
     public String code;

     @Column( nullable = false, length = 200)
     public String name;

     @Column(nullable = false, precision = 12, scale = 2)
     public BigDecimal price; 

     @OneToMany( mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
     public Set<ProductRawMaterial> rawMaterials = new HashSet<>();

}


