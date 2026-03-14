package com.autoflex.application.rawmaterial;

import com.autoflex.domain.RawMaterial;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class ListRawMaterialsUseCase {
    private final IRawMaterialRepository repo;

    public ListRawMaterialsUseCase(IRawMaterialRepository repo) {
        this.repo = repo;
    }

    public List<RawMaterial> execute() {
        return repo.findAll();
    }
}