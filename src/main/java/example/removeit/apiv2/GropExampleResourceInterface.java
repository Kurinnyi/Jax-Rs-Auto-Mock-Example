package example.removeit.apiv2;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/groupsExample")
public interface GropExampleResourceInterface {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{param}")
    public String getSomeInfo(@PathParam("param") String someParam);

}
