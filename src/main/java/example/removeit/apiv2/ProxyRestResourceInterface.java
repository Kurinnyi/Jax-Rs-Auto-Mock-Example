package example.removeit.apiv2;

import example.removeit.Dto;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/proxy")
public interface ProxyRestResourceInterface {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/path")
    public Dto getDto(@QueryParam("hi") String hi);

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/path")
    public Dto addDto(Dto dto);

}

