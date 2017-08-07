package ru.pits.keywords.ccmportal;

import ru.pits.keywords.oapi.SearchingAbonentPacketsList;

import java.util.HashMap;
import java.util.Map;

/**"CCM_Portal: Проверка свойств пакета услуг абонента*/

public class CheckAbonentPackProperties {

    String token;
    String subscriberId;
    String subscriberPackId;
    String packIds;
    String psTimezone;

    public CheckAbonentPackProperties(String token, String subscriberId, String subscriberPackId, String packIds, String psTimezone) {
        this.token = token;
        this.subscriberId = subscriberId;
        this.subscriberPackId = subscriberPackId;
        this.packIds = packIds;
        this.psTimezone = psTimezone;
    }

    /**"OAPI: Получение списка пакетов абонента (/packs/search)".*/
    Map<String, String> result1 = new SearchingAbonentPacketsList(this.token, this.subscriberId, this.subscriberPackId, this.packIds, this.psTimezone).getResult();

    /**Выполнить Выполнить keyword = "OAPI: Получение данных по пакету абонента (/packs/{subscriberPackId})".*/
    Map<String, String> result2 = new GettingAbonentPacketData(this.token, this.subscriberId, this.subscriberPackId, this.packIds, this.psTimezone).getResult();

    public Map<String, Map<String, String>> getResult() {
        Map<String, Map<String, String>> results = new HashMap<>();

        results.put("result1", result1);
        results.put("result2", result2);

        return results;
    }
}
