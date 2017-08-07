package ru.pits.keywords.cart;


import ru.pits.keywords.oapi.cart.AbonentProfile;

import java.util.HashMap;
import java.util.Map;

/**
 * CART: Объект списания абонента
 */


public class WriteOffObject {

    private String subscriptionIdType;
    private String subscriptionIdData;
    private String wooName;
    private String wootype;

    private Map<String, String> inputData = new HashMap<>();

    public Map<String, String> getInputData() {
        return inputData;
    }

    public void setInputData(Map<String, String> inputData) {
        this.inputData.put("subscription_id_type", subscriptionIdType);
        this.inputData.put("subscription_id_data", subscriptionIdData);
    }
    public WriteOffObject(String subscriptionIdType, String subscriberId, String wooName, String wooType) {
        this.subscriptionIdData = subscriberId;
        this.subscriptionIdType = subscriptionIdType;
        this.wooName = wooName;
        this.wootype = wooType;
    }

    /***Выполнить keyword = "OAPI: CART: Профиль абонента"*/
    public String getResult() {
        return new AbonentProfile(getInputData()).execHttpPost();
    }

}
