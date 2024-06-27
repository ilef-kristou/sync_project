package com.example.demo.resource;

import com.example.demo.service.SyncService;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/sync")
public class SyncResource {

    private final SyncService syncService = new SyncService();

    @GET
    @Path("/synchronize")
    public Response synchronize() {
        syncService.synchronize();
        return Response.ok("Synchronization completed.").build();
    }
}
