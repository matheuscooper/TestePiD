package com.autoflex.application.rawmaterial;

import com.autoflex.domain.RawMaterial;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class GetRawMaterialUseCase {
    private final IRawMaterialRepository repo;

    public GetRawMaterialUseCase(IRawMaterialRepository repo) {
        this.repo = repo;
    }

    public RawMaterial execute(Long id) {
        return repo.findById(id).orElseThrow(() -> new NotFoundException("Raw material not found"));
    }
}