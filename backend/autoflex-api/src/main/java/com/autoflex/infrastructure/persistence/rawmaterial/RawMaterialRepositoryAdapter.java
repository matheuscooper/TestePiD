package com.autoflex.infrastructure.persistence.rawmaterial;

import com.autoflex.application.rawmaterial.IRawMaterialRepository;
import com.autoflex.domain.RawMaterial;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class RawMaterialRepositoryAdapter implements IRawMaterialRepository {

    private final RawMaterialPanacheRepository repo;

    public RawMaterialRepositoryAdapter(RawMaterialPanacheRepository repo) {
        this.repo = repo;
    }

    @Override
    public Optional<RawMaterial> findById(Long id) {
        return Optional.ofNullable(repo.findById(id));
    }

    @Override
    public Optional<RawMaterial> findByCode(String code) {
        return repo.find("code", code).firstResultOptional();
    }

    @Override
    public List<RawMaterial> findAll() {
        return repo.listAll();
    }

    @Override
    public void save(RawMaterial rawMaterial) {
        repo.persist(rawMaterial);
    }

    @Override
    public boolean deleteById(Long id) {
        return repo.deleteById(id);
    }
}