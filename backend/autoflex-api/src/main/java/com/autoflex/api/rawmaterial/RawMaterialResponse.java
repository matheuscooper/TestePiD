package com.autoflex.api.rawmaterial;

public class RawMaterialResponse {
    public Long id;
    public String code;
    public String name;
    public Long stockQuantity;

    public static RawMaterialResponse of(com.autoflex.domain.RawMaterial rm) {
        RawMaterialResponse r = new RawMaterialResponse();
        r.id = rm.id;
        r.code = rm.code;
        r.name = rm.name;
        r.stockQuantity = rm.stockQuantity;
        return r;
    }
}