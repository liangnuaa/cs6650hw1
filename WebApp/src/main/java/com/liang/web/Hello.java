package com.liang.web;

/**
 * Created by liangzhang on 9/30/18.
 */
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
@Path("/hello")
public class Hello {
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
