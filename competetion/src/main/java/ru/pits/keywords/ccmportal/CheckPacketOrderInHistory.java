package ru.pits.keywords.ccmportal;

import ru.pits.keywords.oapi.AquisitionDataByOrders;

import java.util.Map;

/**CCM_Portal: Проверка заказов пакета в Истории заказов */

public class CheckPacketOrderInHistory {
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

    public CheckPacketOrderInHistory(String token, String orderIds, String subscriberId,
                                     String productTypeIds, String orderStatusIds,
                                     String productActionIds, String productIds,
                                     String dateFrom, String dateTo, String psTimezone) {
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
    }

    /**Выполнить keyword = "OAPI: Получение данных по заказам (/orders/search)"*/
    public Map<String,String> getResult() {
        return new AquisitionDataByOrders(this.token, this.token, this.orderIds, this.subscriberId,
                this.productTypeIds, this.orderStatusIds,
                this.productActionIds, this.productIds,
                this.dateFrom, this.dateTo, this.psTimezone).getResult();
    }

}
