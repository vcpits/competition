package ru.pits.restClient;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

import javax.ws.rs.core.MediaType;
import java.util.Map;

public class RestClient {
    public RestResponse execute(RestRequest request){
        Client client = Client.create();
        String url = "/ps/auth/api/token";
        //Протокол я так понимаю, что в настройках где-то.

        WebResource resource = client.resource("http://localhost:10102").path("/rest");


        WebResource.Builder builder = resource.getRequestBuilder();
        for(Map.Entry<String, String> headersMapEntry : request.getHeadersMap().entrySet()){
            builder = builder.header(
                    headersMapEntry.getKey(), headersMapEntry.getValue());
        }

        //nтипа как для токена получиь вбил хэдер
//        WebResource.Builder header = resource.header("Content-Type", "application/x-www-form-urlencoded")
//                .header("Accept-Encoding", "gzip, deflate")
//                .header("Host", "vlg-sso-lb1a.megafon.ru:47132")
//                .header("Accept", request.getAccept())
//                .header("Content-Length", "56")
//                .header("Authorization", "Basic {HASH}");
        //А вот это дело из Map<String, String> построить как?

        //Тело для поста предлагают делать так "nj djj,ot dthyj
        /*
        MultivaluedMap formData = new MultivaluedMapImpl();
        formData.add("name1", "val1");
        formData.add("name2", "val2");
        */


        RestResponse response = builder
                .type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .post(RestResponse.class, request.getRequest());

        System.out.println(response);
        //неясно куда вывалится ответ.


        return response;
    }
}