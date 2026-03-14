
package com.autoflex.application.production;

import com.autoflex.application.product.IProductRepository;
import com.autoflex.domain.Product;
import com.autoflex.domain.ProductRawMaterial;
import jakarta.enterprise.context.ApplicationScoped;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class SuggestProductionUseCase {

    private final IProductRepository productRepository;

    public SuggestProductionUseCase(IProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductionSuggestionResult execute() {
        List<Product> products = productRepository.findAll();

        // ordena por maior valor de venda
        products.sort(Comparator.comparing((Product p) -> p.price).reversed());

        // cópia virtual do estoque atual
        Map<Long, Long> availableStock = new HashMap<>();

        for (Product product : products) {
            for (ProductRawMaterial prm : product.rawMaterials) {
                Long rawMaterialId = prm.rawMaterial.id;
                Long stockQuantity = prm.rawMaterial.stockQuantity;

                availableStock.putIfAbsent(rawMaterialId, stockQuantity);
            }
        }

        List<ProductionSuggestionItem> suggestions = new ArrayList<>();
        BigDecimal totalValue = BigDecimal.ZERO;

        for (Product product : products) {
            if (product.rawMaterials == null || product.rawMaterials.isEmpty()) {
                continue;
            }

            long maxUnits = Long.MAX_VALUE;

            for (ProductRawMaterial prm : product.rawMaterials) {
                if(prm.requiredQuantity == null || prm.requiredQuantity <=0) {
                    maxUnits = 0;
                    break;
                }

                long stock = availableStock.getOrDefault(prm.rawMaterial.id, 0L);
                long required = prm.requiredQuantity;

                long possibleUnits = stock / required;
                maxUnits = Math.min(maxUnits, possibleUnits);
            }

            if (maxUnits <= 0) {
                continue;
            }

            // consome estoque virtual
            for (ProductRawMaterial prm : product.rawMaterials) {
                long currentStock = availableStock.getOrDefault(prm.rawMaterial.id, 0L);
                long consumed = maxUnits * prm.requiredQuantity;
                availableStock.put(prm.rawMaterial.id, currentStock - consumed);
            }

            BigDecimal productTotal = product.price.multiply(BigDecimal.valueOf(maxUnits));
            totalValue = totalValue.add(productTotal);

            ProductionSuggestionItem item = new ProductionSuggestionItem();
            item.productId = product.id;
            item.productCode = product.code;
            item.productName = product.name;
            item.unitPrice = product.price;
            item.quantityToProduce = maxUnits;
            item.totalValue = productTotal;

            suggestions.add(item);
        }

        ProductionSuggestionResult result = new ProductionSuggestionResult();
        result.items = suggestions;
        result.totalValue = totalValue;
        return result;
    }
}



