package ru.pits.keywords.oapi.crab;

import com.sun.jersey.core.util.MultivaluedMapImpl;
import ru.pits.restClient.RestClient;
import ru.pits.restClient.RestRequest;

import javax.ws.rs.core.MultivaluedMap;
import java.util.HashMap;
import java.util.Map;

/**OAPI: CRAB: Получение информации об обработанной заявке (/orders/{externalId})
 */

public class GettingPrecessedOrderInfo {

    String externalId = "-";
    /**параметры для http запроса*/
    Map<String, String > headersMap = new HashMap<>();
    MultivaluedMap requestBody = new MultivaluedMapImpl();

    public GettingPrecessedOrderInfo(String externalId) {
        this.externalId = externalId
        this.headersMap.put("url", "/orders/" + externalId);
        this.headersMap.put("Host", "vlg-crab-app1a:8888");
        this.headersMap.put("Accept", "application/json");
        this.headersMap.put("Content-Type", "application/json");
        this.headersMap.put("Accept-Encoding", "gzip, deflate");

    }

    private String execHttpPost() {
        RestClient rc = new RestClient();
        RestRequest rr = new RestRequest(headersMap.get("Host"), headersMap.get("url"));
        rr.setHeadersMap(this.headersMap);
        return rc.execute(rr);
    }

    public Map<String, String> getResult() {
        String responseString = execHttpPost();

        //TODO: когда будет документация, написать парсилку ответа сервера, а пока заглушка
        Map<String, String> response = new HashMap<>();
        response.put("status.state", "success");
        response.put("operation.name", "ccmPackActivate");
        response.put("subscriberPackId", "{1).{subscriberPackId}");
    }
}
