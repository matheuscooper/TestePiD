package com.autoflex.api.productrawmaterial;

import com.autoflex.application.productrawmaterial.AssignRawMaterialToProductCommand;
import com.autoflex.application.productrawmaterial.AssignRawMaterialToProductUseCase;
import com.autoflex.domain.ProductRawMaterial;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/api/products/{productId}/raw-materials")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductRawMaterialResource {

    private final AssignRawMaterialToProductUseCase assignUseCase;

    public ProductRawMaterialResource(AssignRawMaterialToProductUseCase assignUseCase) {
        this.assignUseCase = assignUseCase;
    }

    @POST
    public ProductRawMaterialResponse assign(
            @PathParam("productId") Long productId,
            AssignRawMaterialToProductRequest request
    ) {
        AssignRawMaterialToProductCommand command = new AssignRawMaterialToProductCommand();
        command.productId = productId;
        command.rawMaterialId = request.rawMaterialId;
        command.requiredQuantity = request.requiredQuantity;

        ProductRawMaterial association = assignUseCase.execute(command);
        return ProductRawMaterialResponse.of(association);
    }
}