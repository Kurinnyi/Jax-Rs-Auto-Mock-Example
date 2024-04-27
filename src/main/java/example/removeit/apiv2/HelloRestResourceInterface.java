package example.removeit.apiv2;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Path("/helloworld")
public interface HelloRestResourceInterface {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public String getHello(@QueryParam("hi") String hello, @PathParam("id") long id);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("echo/{id}")
    public long echo(@PathParam("id") long id);

    @PUT
    @Path("noResponse/{id}")
    void noResponse(@PathParam("id") long id);

}

