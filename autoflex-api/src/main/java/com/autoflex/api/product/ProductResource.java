package com.autoflex.api.product;

import com.autoflex.application.product.*;
import com.autoflex.domain.Product;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/api/products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductResource {

    private final CreateProductUseCase create;
    private final ListProductsUseCase list;
    private final GetProductUseCase get;
    private final UpdateProductUseCase update;
    private final DeleteProductUseCase delete;

    public ProductResource(
            CreateProductUseCase create,
            ListProductsUseCase list,
            GetProductUseCase get,
            UpdateProductUseCase update,
            DeleteProductUseCase delete
    ) {
        this.create = create;
        this.list = list;
        this.get = get;
        this.update = update;
        this.delete = delete;
    }

    @POST
    public ProductResponse create(ProductRequest req) {
        CreateProductCommand cmd = new CreateProductCommand();
        cmd.code = req.code;
        cmd.name = req.name;
        cmd.price = req.price;

        Product p = create.execute(cmd);
        return ProductResponse.of(p);
    }

    @GET
    public List<ProductResponse> list() {
        return list.execute().stream().map(ProductResponse::of).toList();
    }

    @GET
    @Path("/{id}")
    public ProductResponse get(@PathParam("id") Long id) {
        return ProductResponse.of(get.execute(id));
    }

    @PUT
    @Path("/{id}")
    public ProductResponse update(@PathParam("id") Long id, ProductRequest req) {
        UpdateProductCommand cmd = new UpdateProductCommand();
        cmd.code = req.code;
        cmd.name = req.name;
        cmd.price = req.price;

        return ProductResponse.of(update.execute(id, cmd));
    }

    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") Long id) {
        delete.execute(id);
    }
}