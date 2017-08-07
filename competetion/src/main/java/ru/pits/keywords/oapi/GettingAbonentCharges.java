package ru.pits.keywords.oapi;

import com.sun.jersey.core.util.MultivaluedMapImpl;
import ru.pits.restClient.RestClient;
import ru.pits.restClient.RestRequest;

import javax.ws.rs.core.MultivaluedMap;
import java.util.HashMap;
import java.util.Map;

public class GettingAbonentCharges {

    private String token;
    private String msisdn;
    private String dateFrom;
    private String packIDs;

    /**параметры для http запроса*/
    Map<String, String > headersMap = new HashMap<>();
    MultivaluedMap requestBody = new MultivaluedMapImpl();

    public GettingAbonentCharges(String token, String msisdn, String dateFrom, String packIDs) {
        this.token = token;
        this.msisdn = msisdn;
        this.dateFrom = dateFrom;
        this.packIDs = packIDs;


        this.headersMap.put("url", "/openapi/v1/subscribers/msisdn:" + msisdn + "/charges/search?limit=0&sort=-chargeDate");
        this.headersMap.put("Host", " vlg-sso-lb1a.megafon.ru:47555");
        this.headersMap.put("Accept", "application/json");
        this.headersMap.put("Authtoken", this.token);

        requestBody.add("packIds", this.packIDs);
        requestBody.add("chargePeriod", new String[] {this.dateFrom});

    }

    private String execHttpPost() {
        RestClient rc = new RestClient();
        RestRequest rr = new RestRequest(headersMap.get("Host"), headersMap.get("url"));
        rr.setHeadersMap(this.headersMap);
        rr.setRequest(this.requestBody);
        return rc.execute(rr);
    }

    public Map<String, String> getResult() {
        String responseString = execHttpPost();
        /**Выходные параметры  ЭТО ЗАГЛУШКА!!!*/
        //TODO: когда будет документация, написать парсилку ответа сервера, а пока заглушка

        Map<String, String> response = new HashMap<>();
        response.put("count", "count");
        response.put("chargeId", "chargeId");
        response.put("subscriptionId", "subscriptionId");
        response.put("chargeStatusId", "chargeStatusId");
        response.put("amount", "amount");
        response.put("eventTypeId", "eventTypeId");

        return response;
    }
}

