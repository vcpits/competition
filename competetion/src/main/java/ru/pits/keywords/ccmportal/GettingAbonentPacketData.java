package ru.pits.keywords.ccmportal;

import com.sun.jersey.core.util.MultivaluedMapImpl;
import ru.pits.restClient.RestClient;
import ru.pits.restClient.RestRequest;

import javax.ws.rs.core.MultivaluedMap;
import java.util.HashMap;
import java.util.Map;

public class GettingAbonentPacketData {

    String token;
    String subscriberId;
    String subscriberPackId;
    String packIds;
    String psTimezone;

    /**параметры для http запроса*/
    Map<String, String > headersMap = new HashMap<>();

    public GettingAbonentPacketData(String token, String subscriberId, String subscriberPackId, String packIds, String psTimezone) {
        this.token = token;
        this.subscriberId = subscriberId;
        this.subscriberPackId = subscriberPackId;
        this.packIds = packIds;
        this.psTimezone = psTimezone;

        this.headersMap.put("url", "openapi/v1/subscribers/{subscriberId}/packs/" + this.subscriberPackId +
                "?fields=pack,status,activationDate,deactivationDate,owner,accountType,packCosts,services," +
                "order,currentSubscriptionFeeDiscount,comment,updateComment,deleteComment,qosServicePlans,ratingDiscounts");
        this.headersMap.put("Host", "vlg-sso-lb1a.megafon.ru:47555");
        this.headersMap.put("Accept", "application/json");
        this.headersMap.put("Authtoken", this.token);
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

        responce.put("discountId", "discountId");
        responce.put("isCallCredit", "isCallCredit");
        responce.put("callCreditId", "callCreditId");
        responce.put("callCreditVolume", "callCreditVolume");
        responce.put("discountStatusId", "discountStatusId");
        responce.put("endDate", "endDate");
        responce.put("volumes", "volumes");

        return responce;
    }


}
