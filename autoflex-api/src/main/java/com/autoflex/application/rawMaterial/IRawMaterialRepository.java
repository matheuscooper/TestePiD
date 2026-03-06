package com.autoflex.application.rawMaterial;

import com.autoflex.domain.RawMaterial;
import java.util.List;
import java.util.Optional;

public interface IRawMaterialRepository {
    List<RawMaterial> findAll();
    Optional<RawMaterial> findById(Long id);
    Optional<RawMaterial> findByCode(String code);

    void save(RawMaterial rawMaterial);
    boolean deleteById(Long id);
}