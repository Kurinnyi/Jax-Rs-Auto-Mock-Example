package example.removeit;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/groupsExample")
public interface GropExampleResourceInterface {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{param}")
    public String getSomeInfo(@PathParam("param") String someParam);

}
