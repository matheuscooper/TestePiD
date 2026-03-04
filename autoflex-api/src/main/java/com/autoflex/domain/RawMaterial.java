package com.autoflex.domain;

import java.util.HashSet;

import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name = "raw_material", 
    uniqueConstraints = @UniqueConstraint(name = "uk_raw_material_code", columnNames = "codes")
) public class RawMaterial {

     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     public Long id;

     @Column(nullable = false, length = 50)
     public String code;

     @Column(nullable = false, length = 200)
     public String name;

     @Column(name = "stock_quantity", nullable = false)
     public Long stockQuantity;

     @OneToMany(mappedBy = "rawMaterial", cascade = CascadeType.ALL, orphanRemoval = true)
     public Set<ProductRawMaterial> products = new HashSet<>();
}