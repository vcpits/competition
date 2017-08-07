package ru.pits.keywords.crab;

import ru.pits.keywords.oapi.crab.GettingPrecessedOrderInfo;

import java.util.Map;

/**
 * CRAB: Получение информации об обработке заказа по пакету  */

public class GettingProcessingPacketInfo {

    private String externalId;

    public GettingProcessingPacketInfo(String externalId) {
        this.externalId = externalId;
    }

    /**  Выполнить keyword = "OAPI: CRAB: Получение информации об обработанной заявке (/orders/{externalId})".*/

    public Map<String,String> getResult() {
        return new GettingPrecessedOrderInfo(this.externalId).getResult();
    }





}
