package ru.pits.restClient;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

public class RestClient {
    public void execute(){
        Client client = Client.create();
        WebResource resource = client.resource("http://localhost:10102").path("/rest");

        WebResource.Builder header = resource.header("Content-Type", "application/x-www-form-urlencoded")
                .header("Accept-Encoding", "gzip, deflate");
        header.post();


    }
}
