package com.autoflex.infrastructure.persistence.rawMaterial;

import com.autoflex.domain.RawMaterial;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RawMaterialPanacheRepository implements PanacheRepository<RawMaterial> {}