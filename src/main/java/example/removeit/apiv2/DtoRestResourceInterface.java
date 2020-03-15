package example.removeit.apiv2;

import example.removeit.Dto;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

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

