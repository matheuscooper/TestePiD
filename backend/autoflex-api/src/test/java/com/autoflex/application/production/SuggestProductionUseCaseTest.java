package com.autoflex.application.production;

import com.autoflex.application.product.IProductRepository;
import com.autoflex.domain.Product;
import com.autoflex.domain.ProductRawMaterial;
import com.autoflex.domain.ProductRawMaterialId;
import com.autoflex.domain.RawMaterial;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class SuggestProductionUseCaseTest {

    @Test
    void shouldSuggestProductionBasedOnHighestSaleValue_withImportSqlData() {
        // raw materials
        RawMaterial carbonFiber = rawMaterial(20L, "RM020", "Carbon Fiber", 90L);
        RawMaterial titaniumBolt = rawMaterial(21L, "RM021", "Titanium Bolt", 300L);
        RawMaterial polycarbonate = rawMaterial(22L, "RM022", "Polycarbonate Sheet", 140L);
        RawMaterial ledModule = rawMaterial(23L, "RM023", "LED Module", 500L);
        RawMaterial lithiumBattery = rawMaterial(24L, "RM024", "Lithium Battery", 200L);
        RawMaterial motorUnit = rawMaterial(25L, "RM025", "Motor Unit", 80L);
        RawMaterial microcontroller = rawMaterial(26L, "RM026", "Microcontroller", 150L);
        RawMaterial touchScreen = rawMaterial(27L, "RM027", "Touch Screen Panel", 120L);

        // products
        Product skateboard = product(20L, "P020", "Electric Skateboard", "4200");
        Product helmet = product(21L, "P021", "Smart Helmet", "1500");
        Product projector = product(22L, "P022", "Portable Projector", "2800");
        Product lamp = product(23L, "P023", "Smart Lamp", "750");
        Product tracker = product(24L, "P024", "Fitness Tracker", "900");
        Product drone = product(25L, "P025", "Mini Drone", "3500");

        // associations
        addRequirement(skateboard, carbonFiber, 4L);
        addRequirement(skateboard, lithiumBattery, 2L);
        addRequirement(skateboard, motorUnit, 1L);
        addRequirement(skateboard, titaniumBolt, 6L);

        addRequirement(helmet, polycarbonate, 3L);
        addRequirement(helmet, ledModule, 4L);
        addRequirement(helmet, microcontroller, 1L);

        addRequirement(projector, ledModule, 6L);
        addRequirement(projector, microcontroller, 2L);
        addRequirement(projector, lithiumBattery, 1L);
        addRequirement(projector, polycarbonate, 2L);

        addRequirement(lamp, ledModule, 3L);
        addRequirement(lamp, microcontroller, 1L);
        addRequirement(lamp, titaniumBolt, 2L);

        addRequirement(tracker, touchScreen, 1L);
        addRequirement(tracker, lithiumBattery, 1L);
        addRequirement(tracker, microcontroller, 1L);

        addRequirement(drone, carbonFiber, 2L);
        addRequirement(drone, motorUnit, 4L);
        addRequirement(drone, lithiumBattery, 2L);
        addRequirement(drone, microcontroller, 1L);

        List<Product> products = List.of(skateboard, helmet, projector, lamp, tracker, drone);

        SuggestProductionUseCase useCase = new SuggestProductionUseCase(new FakeProductRepository(products));

        ProductionSuggestionResult result = useCase.execute();

        assertNotNull(result);
        assertEquals(new BigDecimal("300000"), result.totalValue);
        assertEquals(4, result.items.size());

        assertSuggestion(result.items.get(0), 20L, "P020", "Electric Skateboard", 22L, "92400");
        assertSuggestion(result.items.get(1), 25L, "P025", "Mini Drone", 1L, "3500");
        assertSuggestion(result.items.get(2), 22L, "P022", "Portable Projector", 70L, "196000");
        assertSuggestion(result.items.get(3), 24L, "P024", "Fitness Tracker", 9L, "8100");
    }

    @Test
    void shouldPrioritizeMoreExpensiveProductWhenSharingSameMaterial() {
        RawMaterial battery = rawMaterial(1L, "RM001", "Battery", 10L);

        Product expensive = product(1L, "P001", "Expensive Product", "100");
        Product cheap = product(2L, "P002", "Cheap Product", "60");

        addRequirement(expensive, battery, 5L); // can produce 2
        addRequirement(cheap, battery, 2L);     // could produce 5 if chosen first

        SuggestProductionUseCase useCase = new SuggestProductionUseCase(
                new FakeProductRepository(List.of(expensive, cheap))
        );

        ProductionSuggestionResult result = useCase.execute();

        assertEquals(1, result.items.size());
        assertSuggestion(result.items.get(0), 1L, "P001", "Expensive Product", 2L, "200");
        assertEquals(new BigDecimal("200"), result.totalValue);
    }

    private static void assertSuggestion(
            ProductionSuggestionItem item,
            Long expectedProductId,
            String expectedCode,
            String expectedName,
            Long expectedQty,
            String expectedTotalValue
    ) {
        assertEquals(expectedProductId, item.productId);
        assertEquals(expectedCode, item.productCode);
        assertEquals(expectedName, item.productName);
        assertEquals(expectedQty, item.quantityToProduce);
        assertEquals(new BigDecimal(expectedTotalValue), item.totalValue);
    }

    private static Product product(Long id, String code, String name, String price) {
        Product p = new Product();
        p.id = id;
        p.code = code;
        p.name = name;
        p.price = new BigDecimal(price);
        p.rawMaterials = new HashSet<>();
        return p;
    }

    private static RawMaterial rawMaterial(Long id, String code, String name, Long stockQuantity) {
        RawMaterial rm = new RawMaterial();
        rm.id = id;
        rm.code = code;
        rm.name = name;
        rm.stockQuantity = stockQuantity;
        return rm;
    }

    private static void addRequirement(Product product, RawMaterial rawMaterial, Long requiredQuantity) {
        ProductRawMaterial prm = new ProductRawMaterial();
        prm.id = new ProductRawMaterialId(product.id, rawMaterial.id);
        prm.product = product;
        prm.rawMaterial = rawMaterial;
        prm.requiredQuantity = requiredQuantity;

        if (product.rawMaterials == null) {
            product.rawMaterials = new HashSet<>();
        }

        product.rawMaterials.add(prm);
    }

    static class FakeProductRepository implements IProductRepository {
        private final List<Product> products;

        FakeProductRepository(List<Product> products) {
            this.products = new ArrayList<>(products);
        }

        @Override
        public Optional<Product> findById(Long id) {
            return products.stream().filter(p -> p.id.equals(id)).findFirst();
        }

        @Override
        public Optional<Product> findByCode(String code) {
            return products.stream().filter(p -> p.code.equals(code)).findFirst();
        }

        @Override
        public List<Product> findAll() {
            return new ArrayList<>(products);
        }

        @Override
        public void save(Product product) {
            products.add(product);
        }

        @Override
        public boolean deleteById(Long id) {
            return products.removeIf(p -> p.id.equals(id));
        }
    }
}