package com.autoflex.api.product;

import java.math.BigDecimal;

public class ProductResponse {
    public Long id;
    public String code;
    public String name;
    public BigDecimal price;

    public static ProductResponse of(com.autoflex.domain.Product p) {
        ProductResponse r = new ProductResponse();
        r.id = p.id;
        r.code = p.code;
        r.name = p.name;
        r.price = p.price;
        return r;
    }
}