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
        String url = "/ps/auth/api/token";
        //Протокол я так понимаю, что в настройках где-то.

        WebResource resource = client.resource("http://localhost:10102").path("/rest");

        //nтипа как для токена получиь вбил хэдер
        WebResource.Builder header = resource.header("Content-Type", "application/x-www-form-urlencoded")
                .header("Accept-Encoding", "gzip, deflate")
                .header("Host", "vlg-sso-lb1a.megafon.ru:47132")
                .header("Accept", "application/json")
                .header("Content-Length", "56")
                .header("Authorization", "Basic {HASH}");
        //А вот это дело из Map<String, String> построить как?

        //Тело для поста предлагают делать так "nj djj,ot dthyj
        /*
        MultivaluedMap formData = new MultivaluedMapImpl();
        formData.add("name1", "val1");
        formData.add("name2", "val2");
        */

        header.post();
        //неясно куда вывалится ответ.


    }
}
5