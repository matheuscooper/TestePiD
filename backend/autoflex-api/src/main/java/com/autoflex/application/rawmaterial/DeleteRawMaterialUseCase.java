package com.autoflex.application.rawmaterial;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class DeleteRawMaterialUseCase {

    private final IRawMaterialRepository repo;

    public DeleteRawMaterialUseCase(IRawMaterialRepository repo) {
        this.repo = repo;
    }

    @Transactional
    public void execute(Long id) {
        boolean deleted = repo.deleteById(id);
        if (!deleted) throw new NotFoundException("Raw material not found");
    }
}