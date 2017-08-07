package ru.pits.keywords.oapi;

import com.sun.jersey.core.util.MultivaluedMapImpl;
import ru.pits.restClient.RestClient;
import ru.pits.restClient.RestRequest;

import javax.ws.rs.core.MultivaluedMap;
import java.util.HashMap;
import java.util.Map;

public class SearchingAbonentPacketsList {

    String token;
    String subscriberId;
    String subscriberPackId;
    String packIds;
    String psTimezone;

    /**параметры для http запроса*/
    Map<String, String > headersMap = new HashMap<>();
    MultivaluedMap requestBody = new MultivaluedMapImpl();


    public SearchingAbonentPacketsList(String token, String subscriberId, String subscriberPackId, String packIds, String psTimezone) {
        this.token = token;
        this.subscriberId = subscriberId;
        this.subscriberPackId = subscriberPackId;
        this.packIds = packIds;
        this.psTimezone = psTimezone;

        this.headersMap.put("url", "/openapi/v1/subscribers/" + this.subscriberId + "/packs/search?limit=0&sort=-activationDate");
        this.headersMap.put("Host", "vlg-sso-lb1a.megafon.ru:47555");
        this.headersMap.put("Content-Type", "application/json");
        this.headersMap.put("Accept", "application/json");
        this.headersMap.put("Authtoken", this.token);
        this.headersMap.put("ps-timezone", this.psTimezone);

        this.requestBody.add("packStatusIds", ["1", "2", "3", "4", "5", "7"]);
        this.requestBody.add("packIds", this.packIds);
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
        /**Выходные параметры*/
        //TODO: когда будет документация, написать парсилку ответа сервера, а пока заглушка

        Map<String, String> responce = new HashMap<>();

        responce.put("activationDate", "activationDate");
        responce.put("deactivationDat", "deactivationDat");
        responce.put("pack.packId", "pack.packId");
        responce.put("pack.name", "pack.name");
        responce.put("status.name", "status.name");
        responce.put("packStatusId", "packStatusId");
        responce.put("accountTypeId", "accountTypeId");
        responce.put("subscriberPackId", "subscriberPackId");
        responce.put("activationCost", "activationCost");
        responce.put("amount", "amount");

        return responce;
    }

}
