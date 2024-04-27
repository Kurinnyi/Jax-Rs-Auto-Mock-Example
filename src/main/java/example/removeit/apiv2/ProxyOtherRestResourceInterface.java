package example.removeit.apiv2;

import example.removeit.Dto;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Path("/other")
public interface ProxyOtherRestResourceInterface {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/proxy/path")
    public Dto getDto(@QueryParam("hi") String hi);


    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/proxy/path")
    public Dto addDto(Dto dto);
}

