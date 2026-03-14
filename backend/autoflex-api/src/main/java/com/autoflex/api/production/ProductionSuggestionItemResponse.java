package com.autoflex.api.production;


import com.autoflex.application.production.ProductionSuggestionItem;

import java.math.BigDecimal;

public class ProductionSuggestionItemResponse {
    public Long productId;
    public String productCode;
    public String productName;
    public BigDecimal unitPrice;
    public Long quantityToProduce;
    public BigDecimal totalValue;

    public static ProductionSuggestionItemResponse of(ProductionSuggestionItem item) {
        ProductionSuggestionItemResponse response = new ProductionSuggestionItemResponse();
        response.productId = item.productId;
        response.productCode = item.productCode;
        response.productName = item.productName;
        response.unitPrice = item.unitPrice;
        response.quantityToProduce = item.quantityToProduce;
        response.totalValue = item.totalValue;
        return response;
    }
}