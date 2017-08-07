package ru.pits.keywords.oapi;

import com.sun.jersey.core.util.MultivaluedMapImpl;
import ru.pits.restClient.RestClient;
import ru.pits.restClient.RestRequest;

import javax.ws.rs.core.MultivaluedMap;
import java.util.HashMap;
import java.util.Map;

/** keyword = "OAPI: Получение данных по заказам (/orders/search)"*/


public class AquisitionDataByOrders {
    private String token;
    private String orderIds;
    private String subscriberId;
    private String productTypeIds;
    private String orderStatusIds;
    private String productActionIds;
    private String productIds;
    private String dateFrom;
    private String dateTo;
    private String psTimezone;

    /**параметры для http запроса*/
    Map<String, String > headersMap = new HashMap<>();
    MultivaluedMap requestBody = new MultivaluedMapImpl();

    public AquisitionDataByOrders(String token, String orderIds, String subscriberId,
                                  String productTypeIds, String orderStatusIds,
                                  String productActionIds, String productIds,
                                  String dateFrom, String dateTo, String psTimezone, String timezone) {
        this.token = token;
        this.orderIds = orderIds;
        this.subscriberId = subscriberId;
        this.productTypeIds = productTypeIds;
        this.orderStatusIds = orderStatusIds;
        this.productActionIds = productActionIds;
        this.productIds = productIds;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.psTimezone = psTimezone;

        this.headersMap.put("url", "/openapi/v1/orders/search?limit=0&sort=-orderId");
        this.headersMap.put("Host", " vlg-sso-lb1a.megafon.ru:47555");
        this.headersMap.put("Accept", "application/json");
        this.headersMap.put("Authtoken", this.token);
        this.headersMap.put("ps-timezone", this.psTimezone);

        setRequestBody();

    }

    private void setRequestBody() {
        if(!this.orderIds.isEmpty())
            requestBody.add("orderIds", this.orderIds);
        if(!this.orderStatusIds.isEmpty())
            requestBody.add("orderStatusIds", this.orderStatusIds);
        if(!this.productActionIds.isEmpty())
            requestBody.add("productActionIds", this.productActionIds);
        if(!this.productTypeIds.isEmpty())
            requestBody.add("productTypeIds", this.productTypeIds);
        if(!this.productIds.isEmpty())
            requestBody.add("productIds", this.productIds);
        if(!this.subscriberId.isEmpty())
            requestBody.add("subscriberId", this.subscriberId);
        if(!this.dateFrom.isEmpty() && !this.dateTo.isEmpty())
            requestBody.add("createDateRange", "\"dateFrom\":\"" + this.dateFrom + "\", \n" +
                    "\"dateTo\":\"" + this.dateTo + "\"");

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
        /*
        orderId идентификатор заказа=
        productId идентификатор продукта=
        productName имя продукта=
        customerId идентификатор клиента=
        subscriberId идентификатор абонента=
        type.orderTypeId идентификатор типа заказа
        type.code код типа заказа
        type.name название типа заказа

        orderStatusId 3 (Выполнен)=
        orderTypeId 2 (Пакеты услуг -  Добавление или    активация продукта)
        crab_body.deactivationDate (p3).{DURATION_LIMIT_DATE}
        productInstanceId (1).{subscriberPackId}
        */
        Map<String, String> responce = new HashMap<>();
        responce.put("orderId", "order_id");
        responce.put("productId", "productId");
        responce.put("productName", "productName");
        responce.put("customerId", "customerId");
        responce.put("subscriberId", "subscriberId");
        responce.put("type.orderTypeId ", "type.orderTypeId");
        responce.put("type.code", "type.code");
        responce.put("type.name", "type.name");
        responce.put("orderStatusId", "3");
        responce.put("orderTypeId", "2");
        responce.put("crab_body.deactivationDate", "3");
        responce.put("productInstanceId", "(1).{subscriberPackId}");

        return responce;
    }


}
