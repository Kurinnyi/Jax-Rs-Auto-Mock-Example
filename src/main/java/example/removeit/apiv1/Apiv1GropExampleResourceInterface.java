package example.removeit.apiv1;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Path("/groupsExample")
public interface Apiv1GropExampleResourceInterface {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{param}")
    public String getSomeInfo(@PathParam("param") String someParam);

}
