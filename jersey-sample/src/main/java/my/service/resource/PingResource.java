package my.service.resource;


import java.util.Map;
import java.util.HashMap;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/ping")
public class PingResource {

//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    @Consumes(MediaType.WILDCARD)
//    public Response createPet() {
//        Map<String, String> pong = new HashMap<>();
//        pong.put("pong", "Hello, World!");
//        return Response.status(200).entity(pong).build();
//    }
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getStatus() {
    return ("successfully deployed");
}

    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    public int postText(String content) {
        return (content.length());
    }
}