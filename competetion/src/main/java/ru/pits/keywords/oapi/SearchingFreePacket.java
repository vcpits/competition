package ru.pits.keywords.oapi;

import ru.pits.keywords.oapi.cart.AbonentProfile;

/**OAPI: Поиск бесплатного пакета для подключения"*/

public class SearchingFreePacket {

    /**Входные параметры */

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
    public String getPackID() {
        return null;
    }

}
