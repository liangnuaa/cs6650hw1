package com.hw1;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

/**
 * Created by liangzhang on 10/7/18.
 */
public class WebClient {
    private String url;
    private Client client = ClientBuilder.newClient();

    public WebClient(String url) {
        this.url = url;
    }

    public String getStatus() throws ClientErrorException {
        return client
                .target(url)
                .request(MediaType.TEXT_PLAIN)
                .get(String.class);
    }

    public <T> T postText(Object requestEntity, Class<T> responseType) throws
            ClientErrorException {
        return client
                .target(url)
                .request(MediaType.TEXT_PLAIN)
                .post(Entity.entity(requestEntity, MediaType.TEXT_PLAIN), responseType);
    }
}
