package com.liang.client;

import com.sun.corba.se.impl.protocol.giopmsgheaders.Message;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

/**
 * Created by liangzhang on 9/30/18.
 */
public class WebClient {
    private Client client = ClientBuilder.newClient();
    private WebTarget webTarget = client.target("http://localhost:8081/api/hello");

    public <T> T postText(Object requestEntity, Class<T> responseType) throws
            ClientErrorException {
        return webTarget.request(javax.ws.rs.core.MediaType.TEXT_PLAIN)
                        .post(javax.ws.rs.client.Entity.entity(requestEntity,
                                javax.ws.rs.core.MediaType.TEXT_PLAIN),
                                responseType);
    }
    public String getStatus() throws ClientErrorException {

        return webTarget.request(javax.ws.rs.core.MediaType.TEXT_PLAIN).get(String.class);
    }

    public static void main(String[] args) {
        WebClient client = new WebClient();
        System.out.println(client.getStatus());
//        Client client = ClientBuilder.newClient();
//        Response response = client.target("http://localhost:8081/api/hello").request().get();
//        Message message = response.readEntity(Message.class);
//        System.out.println(message);
    }
}
