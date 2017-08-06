package ru.pits.restClient;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import javax.ws.rs.core.MediaType;
import java.util.Map;

public class RestClient {
    public String execute(RestRequest request){
        Client client = Client.create();
        WebResource resource = client.resource(request.getUrl()).path(request.getPath());

        WebResource.Builder builder = resource.getRequestBuilder();
        for(Map.Entry<String, String> headersMapEntry : request.getHeadersMap().entrySet()){
            builder = builder.header(
                    headersMapEntry.getKey(), headersMapEntry.getValue());
        }

        ClientResponse response = builder
                .type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, request.getRequest());

        return response.getEntity(String.class);
    }
}

