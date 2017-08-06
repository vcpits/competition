package ru.pits.restClient;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

import javax.ws.rs.core.MediaType;
import java.util.Map;

public class RestClient {
    public RestResponse execute(RestRequest request){
        Client client = Client.create();
        String url = "/ps/auth/api/token";

        WebResource resource = client.resource("http://localhost:10102").path("/rest");


        WebResource.Builder builder = resource.getRequestBuilder();
        for(Map.Entry<String, String> headersMapEntry : request.getHeadersMap().entrySet()){
            builder = builder.header(
                    headersMapEntry.getKey(), headersMapEntry.getValue());
        }


        RestResponse response = builder
                .type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .post(RestResponse.class, request.getRequest());

        System.out.println(response);

        return response;
    }
}

