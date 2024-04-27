package example.removeit.apiv1;

import example.removeit.Dto;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Path("/helloworld")
public interface Apiv1DtoRestResourceInterface {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/dto")
    public Dto getDto();

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/dto")
    public Dto addDto(Dto dto);

}

