package com.example.demo.util;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

public class RestClient {
    private final Client client;

    public RestClient() {
        this.client = ClientBuilder.newClient();
    }

    public String get(String url) throws IOException {
        WebTarget target = client.target(url);
        Response response = target.request(MediaType.APPLICATION_JSON).get();
        if (response.getStatus() != 200) {
            throw new IOException("Failed : HTTP error code : " + response.getStatus());
        }
        return response.readEntity(String.class);
    }

    public void post(String url, String json) throws IOException {
        WebTarget target = client.target(url);
        Response response = target.request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(json, MediaType.APPLICATION_JSON));
        if (response.getStatus() != 200 && response.getStatus() != 201) {
            throw new IOException("Failed : HTTP error code : " + response.getStatus());
        }
    }
}