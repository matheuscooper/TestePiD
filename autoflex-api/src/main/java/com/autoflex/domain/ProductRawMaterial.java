package com.autoflex.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "product_raw_material")
public class ProductRawMaterial {

    @EmbeddedId
    public ProductRawMaterialId id;

    @ManyToOne(optional = false)
    @MapsId("productId")
    @JoinColumn(
        name = "product_id",
        nullable = false,
        foreignKey = @ForeignKey(name = "fk_prm_product")
    )
    public Product product;

    @ManyToOne(optional = false)
    @MapsId("rawMaterialId")
    @JoinColumn(
        name = "raw_material_id",
        nullable = false,
        foreignKey = @ForeignKey(name = "fk_prm_raw_material")
    )
    public RawMaterial rawMaterial;

    @Column(name = "required_quantity", nullable = false)
    public Long requiredQuantity;
}