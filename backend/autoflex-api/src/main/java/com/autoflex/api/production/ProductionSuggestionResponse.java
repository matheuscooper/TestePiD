package com.autoflex.api.production;

import com.autoflex.application.production.ProductionSuggestionResult;


import java.math.BigDecimal;
import java.util.List;

public class ProductionSuggestionResponse {
    public List<ProductionSuggestionItemResponse> items;
    public BigDecimal totalValue;

    public static ProductionSuggestionResponse of(ProductionSuggestionResult result) {
        ProductionSuggestionResponse response = new ProductionSuggestionResponse();
        response.items = result.items.stream()
                .map(ProductionSuggestionItemResponse::of)
                .toList();
        response.totalValue = result.totalValue;
        return response;
    }
}