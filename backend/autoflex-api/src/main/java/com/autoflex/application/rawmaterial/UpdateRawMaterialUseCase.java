package com.autoflex.application.rawmaterial;

import com.autoflex.domain.RawMaterial;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class UpdateRawMaterialUseCase {

    private final IRawMaterialRepository repo;

    public UpdateRawMaterialUseCase(IRawMaterialRepository repo) {
        this.repo = repo;
    }

    @Transactional
    public RawMaterial execute(Long id, UpdateRawMaterialCommand cmd) {
        CreateRawMaterialUseCase.validate(cmd.code, cmd.name, cmd.stockQuantity);

        RawMaterial rm = repo.findById(id).orElseThrow(() -> new NotFoundException("Raw material not found"));

        String newCode = cmd.code.trim();
        if (!newCode.equals(rm.code)) {
            repo.findByCode(newCode).ifPresent(x -> {
                throw new IllegalArgumentException("Raw material code already exists");
            });
        }

        rm.code = newCode;
        rm.name = cmd.name.trim();
        rm.stockQuantity = cmd.stockQuantity;

        return rm;
    }
}