package example.removeit.apiv1;

import example.removeit.Dto;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Path("/proxy")
public interface Apiv1ProxyRestResourceInterface {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/path")
    public Dto getDto(@QueryParam("hi") String hi);

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/path")
    public Dto addDto(Dto dto);

}

