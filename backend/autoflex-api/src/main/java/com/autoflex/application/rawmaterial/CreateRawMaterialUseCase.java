package com.autoflex.application.rawmaterial;

import com.autoflex.domain.RawMaterial;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class CreateRawMaterialUseCase {

    private final IRawMaterialRepository repo;

    public CreateRawMaterialUseCase(IRawMaterialRepository repo) {
        this.repo = repo;
    }

    @Transactional
    public RawMaterial execute(CreateRawMaterialCommand cmd) {
        validate(cmd.code, cmd.name, cmd.stockQuantity);

        String code = cmd.code.trim();
        repo.findByCode(code).ifPresent(x -> {
            throw new IllegalArgumentException("Raw material code already exists");
        });

        RawMaterial rm = new RawMaterial();
        rm.code = code;
        rm.name = cmd.name.trim();
        rm.stockQuantity = cmd.stockQuantity;

        repo.save(rm);
        return rm;
    }

    static void validate(String code, String name, Long stockQty) {
        if (code == null || code.isBlank()) throw new IllegalArgumentException("code is required");
        if (name == null || name.isBlank()) throw new IllegalArgumentException("name is required");
        if (stockQty == null) throw new IllegalArgumentException("stockQuantity is required");
        if (stockQty < 0) throw new IllegalArgumentException("stockQuantity must be >= 0");
    }
}