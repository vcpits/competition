package ru.pits.keywords.oapi.cart;

import com.sun.jersey.core.util.MultivaluedMapImpl;
import ru.pits.restClient.RestClient;
import ru.pits.restClient.RestRequest;

import javax.ws.rs.core.MultivaluedMap;
import java.util.HashMap;
import java.util.Map;

/**OAPI: CART: Профиль абонента*/

public class AbonentProfile {

    Map<String, String> defaultParams = new HashMap<>();

    /**параметры для http запроса*/
    Map<String, String > headersMap = new HashMap<>();
    MultivaluedMap requestBody = new MultivaluedMapImpl();

    public AbonentProfile(Map<String, String> params) {
        setDefaultParams();

        for(Map.Entry<String, String> paramEntry : params.entrySet())
            this.defaultParams.put(paramEntry.getKey(), paramEntry.getValue());
    }

    public AbonentProfile() {
        setDefaultParams();
    }

    private void setHeadersMap() {
        headersMap.put("url", "api/d_prof");
        headersMap.put("Content-Type", "application/x-www-form-urlencoded");
        headersMap.put("charset", "UTF-8");
        headersMap.put("Host", "vlg-lcart-app1a:7007");
        headersMap.put("Authorization", "Basic " + this.defaultParams.get("hash"));
    }

    private void setDefaultParams() {
        defaultParams.put("subscription_id_type", "5");
        defaultParams.put("subscription_id_data", "-");
        defaultParams.put("hash", "YWRtaW46YWRtaW4=");
    }

    //в данном keyword тело пост запроса заполняется "хитро", поэтому делаем отдельную функцию
    private String setBodyValue() {
        return "cmd=get_profile&key=%7B%22subscription_id%2" +
                "2%3A%7B%22subscription_id_type%22%3A5%2C" +
                "%22subscription_id_data%22%3A%2286685%22" +
                "%7D%7D";
    }

    public String execHttpPost() {
        RestClient rc = new RestClient();
        RestRequest rr = new RestRequest(headersMap.get("Host"), headersMap.get("url"));
        rr.setHeadersMap(this.headersMap);
        rr.setRequest( new MultivaluedMapImpl().add(setBodyValue()));
        return rc.execute(rr);
    }

/*
    subscription_id_type тип идентификатора 5
    subscription_id_data идентификатор -
    HASH base64 хеш строки "login:password" пользователя YWRtaW46YWRtaW4=

    Метод: POST;
    URL: api/d_prof
    Протокол: HTTP/1.1
    Заголовки:
    Content-Type: application/x-www-form-urlencoded;
    charset=UTF-8
    Host: vlg-lcart-app1a:7007
    Authorization: Basic {HASH}
    Тело запроса:
    cmd:get_profile
    key:{"subscription_id":{"subscription_id_type":
        {subscription_id_type},"subscription_id_data":"
        {subscription_id_data}"}}
*/

}
