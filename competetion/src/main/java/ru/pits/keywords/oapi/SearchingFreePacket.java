package ru.pits.keywords.oapi;

import ru.pits.keywords.oapi.cart.AbonentProfile;

import java.util.Map;

/**OAPI: Поиск бесплатного пакета для подключения"*/

public class SearchingFreePacket {

    /**Входные параметры */
    private String token;
    private String subscriberId;
    private Map<int, Map<String, String>> packList;
    private Map<int, Map<String, String>> clientData;
    private String psTimezone;

    private int counterTry = 0; //если не находим на одним наборе данных клиент-таймзона, то нам нужна другая пара клиент-таймзона

    /**Выходное значение*/
    public String packId = null;

    public SearchingFreePacket(String token, Map<int, Map<String, String>> subsIdAndTZ , Map<int, Map<String, String>> packList) {
        this.token = token;
        this.packList = packList;
        this.clientData = subsIdAndTZ;
    }
    do{
        private void setSubscriberIdAndTZ() {
            this.subscriberId = this.clientData.get(String.valueOf(counterTry)).get("SUBS_ID");
            this.psTimezone = this.clientData.get(String.valueOf(counterTry)).get("TZNAME");
            counterTry++;
        }

    /*  Для выполнения этого keyword необходимо выполнить следующие keywords:
        Шаг 1: OAPI: Получение списка пакетов, доступных для подключения абоненту (packs/availableForActivate)
        Шаг 2:OAPI: Определение параметров подключения пакета (activate/parameters)

        Вышеперечисленные keywords заказчик не передал поэтому оставляю заглушку.
    */

        /** Шаг 3: Проверить отсутствие в CART профиля активации для выбранного пакета
         Выполнить keyword = "OAPI: CART: Профиль абонента" */

        String abonentProfileRequest = new AbonentProfile().execHttpPost(); //Ответ от сервера получен. что делать с ним дальше неизвестно.


        /*
            Шаг 4: OAPI: Получение разовых начислений за подключение пакета (activate/charges)
            Шаг 5: OAPI: Проверка возможности подключения пакета (activate/check)

            Вышеперечисленные keywords заказчик не передал поэтому оставляю заглушку.

        */
        this.packId = "";
    }while(!getReturnSuccesStatement())

    //Признак того, что мы нашли верный packID
    private boolean getReturnSuccesStatement() {
        return true;
    }
    public String getPackID() {
        return "будем считать, что какой-то пакет мы таки нашли ;)";
    }

}
