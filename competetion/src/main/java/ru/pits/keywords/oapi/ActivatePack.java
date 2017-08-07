package ru.pits.keywords.oapi;

import com.sun.jersey.core.util.MultivaluedMapImpl;
import ru.pits.restClient.RestClient;
import ru.pits.restClient.RestRequest;

import javax.ws.rs.core.MultivaluedMap;
import java.util.HashMap;
import java.util.Map;

public class ActivatePack {

    String token;
    String subscriberId;
    String packId;
    Boolean checkBalance;
    String accountTypeId;
    String activationDate;
    String psTimezone;

    /**параметры для http запроса*/
    Map<String, String > headersMap = new HashMap<>();
    MultivaluedMap requestBody = new MultivaluedMapImpl();

    public ActivatePack(String token, String subscriberId, String packId, Boolean checkBalance,
                        String accountTypeId, String activationDate, String psTimezone) {
        this.token = token;
        this.subscriberId = subscriberId;
        this.packId = packId;
        this.checkBalance = checkBalance;
        this.accountTypeId = accountTypeId;
        this.activationDate = activationDate;
        this.psTimezone = psTimezone;

        //заполняем хэдер
        this.headersMap.put("url", "openapi/v1/common/batchExecute?breakOnError=true");
        this.headersMap.put("Host", "vlg-sso-lb1a.megafon.ru:47555");
        this.headersMap.put("Accept", "application/json");
        this.headersMap.put("Authtoken", this.token);
        this.headersMap.put("ps-timezone", this.psTimezone);

        setRequestBody();
    }

    private void setRequestBody() {
        String body = "[\n" +
                "{\n" +
                "\"requestBody\": {\n" +
                "\"packId\": " + this.packId + ",\n" +
                "\"actionParameters\": {\n" +
                "\"checkBalance\": " + this.checkBalance + ",\n" +
                "\"accountTypeId\": " + accountTypeId + ",\n" +
                "\"optionalChargeIds\": []\n" +
                "},\n" +
                "\"activationDate\": \"" + this.activationDate + "\"\n" +
                "},\n" +
                "\"requestId\": \"activate_" + this.packId + "\",\n" +
                "\"method\": \"POST\",\n" +
                "\"internal\": true,\n" +
                "\"url\": \"openapi/v1/subscribers/{subscriberId}/packs/activate\"\n" +
                "}\n" +
                "]\n" +
                "}\n}";

        this.requestBody.add("functions", body);
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
        responce.put("orderId", "order_id");
        responce.put("subscriberPackId", "subscriberPackId");

        return responce;
    }
}
