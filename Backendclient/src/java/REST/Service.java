package REST;

import javax.json.*;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;

/**
 *
 * @author Casper
 */
@Path("")
public class Service {
    Database db = new Database("localhost:3306/absentReport?useSSL=false", "root", "Admin123");

    //GET
    /**
        @return True if database is connected.
    */
    @GET
    @Path("/testConnection")
    public Response getTest(){
        
        if (db.testConnection()) {
            return Response.ok().build();
        } else {
            return Response.serverError().build();
        }
    }
    
    @GET
    @Path("/nrEmployed")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNrEmployers(){
        JsonObject JO = db.getNrEmployed();

        if (JO == null) {
            return Response.noContent().build();
        }
        
        return Response.ok(JO).build();
    }
    
    @GET
    @Path("/statusInfo")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStatusInfo(){
        JsonArray JA = db.getStatusInfo();
        
        if (JA == null) {
            return Response.noContent().build();
        }
        
        return Response.ok(JA).build();
    }
    
    @GET
    @Path("/userInfo/{userID}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserInfo(@PathParam("userID") int userID){
        JsonObject JO = db.getUserInfo(userID);
        
        if (JO == null) {
            return Response.noContent().build();
        }
        
        return Response.ok(JO).build();
    }
    
    @GET
    @Path("/away")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAway(){
        JsonArray JA = db.getAway();
        
        if (JA == null) {
            return Response.noContent().build();
        }
        
        return Response.ok(JA).build();
    }

    @GET
    @Path("/employed")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEmployed(){
        JsonArray JA = db.getEmployed();
        
        if (JA == null) {
            return Response.noContent().build();
        }
        
        return Response.ok(JA).build();
    }
    
    @GET
    @Path("/nrAway")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNrAway(){
        JsonObject JO = db.getNrAway();
        
        if (JO == null) {
            return Response.noContent().build();
        }
        
        return Response.ok(JO).build();
    }
    
    //POST
    
    @POST
    @Path("/authenticate")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response authenticate(String body){
        int userID = db.authenticate(body);
        
        JsonObjectBuilder JOB = Json.createObjectBuilder();
        JOB.add("userID", userID);

        if (userID > 0) {

            return Response.ok(JOB.build()).build();
        } else {
            return Response.ok(JOB.build()).build();
        }
    }
    
    //PUT
    
    @PUT
    @Path("/submitLeave")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response putSubmitLeave(String body){
        boolean response = db.putLeave(body);
        
        if (response) {
            JsonObjectBuilder JOB = Json.createObjectBuilder();
            JOB.add("Status", 200);
            return Response.ok(JOB.build()).build();
        } else {
            return Response.serverError().build();
        }
        
    }
    
}
