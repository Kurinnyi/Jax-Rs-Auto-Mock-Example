package example.removeit.apiv2;

import example.removeit.Dto;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/helloworld")
public interface DtoRestResourceInterface {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/dto")
    public Dto getDto();

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/dto")
    public Dto addDto(Dto dto);

}

