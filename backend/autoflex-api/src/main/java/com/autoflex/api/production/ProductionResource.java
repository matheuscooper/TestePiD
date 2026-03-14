package com.autoflex.api.production;

import com.autoflex.application.production.ProductionSuggestionResult;
import com.autoflex.application.production.SuggestProductionUseCase;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/api/production")
@Produces(MediaType.APPLICATION_JSON)
public class ProductionResource {

    private final SuggestProductionUseCase suggestProductionUseCase;

    public ProductionResource(SuggestProductionUseCase suggestProductionUseCase) {
        this.suggestProductionUseCase = suggestProductionUseCase;
    }

    @GET
    @Path("/suggestion")
    public ProductionSuggestionResponse suggest() {
        ProductionSuggestionResult result = suggestProductionUseCase.execute();
        return ProductionSuggestionResponse.of(result);
    }
}