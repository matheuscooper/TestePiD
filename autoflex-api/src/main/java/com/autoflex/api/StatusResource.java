package com.autoflex.api;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.time.OffsetDateTime;
import java.util.Map;

@Path("/api/status")
public class StatusResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> status() {
        return Map.of(
            "app", "autoflex-api",
            "status", "ok",
            "time", OffsetDateTime.now().toString()
        );
    }
}