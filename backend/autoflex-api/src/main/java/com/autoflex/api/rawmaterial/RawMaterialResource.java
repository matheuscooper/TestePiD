package com.autoflex.api.rawmaterial;

import com.autoflex.application.rawmaterial.*;
import com.autoflex.domain.RawMaterial;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/api/raw-materials")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RawMaterialResource {

    private final CreateRawMaterialUseCase create;
    private final ListRawMaterialsUseCase list;
    private final GetRawMaterialUseCase get;
    private final UpdateRawMaterialUseCase update;
    private final DeleteRawMaterialUseCase delete;

    public RawMaterialResource(
            CreateRawMaterialUseCase create,
            ListRawMaterialsUseCase list,
            GetRawMaterialUseCase get,
            UpdateRawMaterialUseCase update,
            DeleteRawMaterialUseCase delete
    ) {
        this.create = create;
        this.list = list;
        this.get = get;
        this.update = update;
        this.delete = delete;
    }

    @POST
    public RawMaterialResponse create(RawMaterialRequest req) {
        CreateRawMaterialCommand cmd = new CreateRawMaterialCommand();
        cmd.code = req.code;
        cmd.name = req.name;
        cmd.stockQuantity = req.stockQuantity;

        RawMaterial rm = create.execute(cmd);
        return RawMaterialResponse.of(rm);
    }

    @GET
    public List<RawMaterialResponse> list() {
        return list.execute().stream().map(RawMaterialResponse::of).toList();
    }

    @GET
    @Path("/{id}")
    public RawMaterialResponse get(@PathParam("id") Long id) {
        return RawMaterialResponse.of(get.execute(id));
    }

    @PUT
    @Path("/{id}")
    public RawMaterialResponse update(@PathParam("id") Long id, RawMaterialRequest req) {
        UpdateRawMaterialCommand cmd = new UpdateRawMaterialCommand();
        cmd.code = req.code;
        cmd.name = req.name;
        cmd.stockQuantity = req.stockQuantity;

        return RawMaterialResponse.of(update.execute(id, cmd));
    }

    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") Long id) {
        delete.execute(id); // 204 No Content
    }
}