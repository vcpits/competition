package ru.pits.keywords.oapi;

import ru.pits.restClient.RestClient;
import ru.pits.restClient.RestRequest;

import java.util.HashMap;
import java.util.Map;

public class GettingAbonentPackHistory {

    String token;
    String subscriberId;
    String packId;
    String activationDate;
    String deactivationDate;
    String packStatusIds
    String psTimezone;

    /**параметры для http запроса*/
    Map<String, String > headersMap = new HashMap<>();

    public GettingAbonentPackHistory(String token, String subscriberId, String packId,
                                     String activationDate, String deactivationDate, String packStatusIds,
                                     String psTimezone) {
        this.token = token;
        this.subscriberId = subscriberId;
        this.packId = packId;
        this.psTimezone = psTimezone;
        this.activationDate = activationDate;
        this.deactivationDate = deactivationDate;
        this.packStatusIds = packStatusIds;

        this.headersMap.put("url", "/openapi/v1/subscribers/" + this.subscriberId + "/packs/" + this.packId + "/history?" +
                "activationDate.dateFrom=" + this.activationDate + "&activationDate.dateTo=&deactivationDate.dateFrom=" +
                this.deactivationDate + "&deactivationDate.dateTo=&packStatusIds={packStatusIds}&limit=0");
        this.headersMap.put("Accept", "application/json");
        this.headersMap.put("authToken", this.token);
        this.headersMap.put("ps-timezone", this.psTimezone);
    }

    private String execHttpPost() {
        RestClient rc = new RestClient();
        RestRequest rr = new RestRequest(headersMap.get("Host"), headersMap.get("url"));
        rr.setHeadersMap(this.headersMap);
        return rc.execute(rr);
    }

    public Map<String, String> getResult() {
        String responseString = execHttpPost();
        /**Выходные параметры*/
        //TODO: когда будет документация, написать парсилку ответа сервера, а пока заглушка

        Map<String, String> responce = new HashMap<>();
        responce.put("activationDate","activationDate");
        responce.put("deactivationDate","deactivationDate");
        responce.put("createUser","createUser");
        responce.put("changeUser","changeUser");
        responce.put("comment","comment");
        responce.put("changeDate","changeDate");
        responce.put("status.packStatusId","status.packStatusId");
        responce.put("status.name","status.name");
        responce.put("category.packCategoryId","category.packCategoryId");
        responce.put("category.name","category.name");
        responce.put("marketingCampaign","marketingCampaign");

        return responce;
    }



}
