package ru.pits.restClient;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import java.util.Map;

public class RestClient {
    public void execute(String path, Map<String, String> headersMap, String body){
        Client client = Client.create();
        String url = headersMap.get("host");// "/ps/auth/api/token";

        //Протокол я так понимаю, что в настройках где-то.

        WebResource resource = client.resource("http://localhost:10102").path("path");

        /**Собираем header */
        WebResource.Builder builder = resource.getRequestBuilder();
        for(Map.Entry<String, String> headersMapEntry : headersMap.entrySet()){
            builder = builder.header(
                    headersMapEntry.getKey(), headersMapEntry.getValue());
        }

        //Тело для поста предлагают делать так ЭТО вообще верно???
        /*
        MultivaluedMap formData = new MultivaluedMapImpl();
        formData.add("name1", "val1");
        formData.add("name2", "val2");
        */

        builder.post();
        //неясно куда вывалится ответ.


    }
}