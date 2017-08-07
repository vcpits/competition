package ru.pits;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.pits.keywords.GettingToken;
import ru.pits.keywords.ccmportal.CheckPacketOrderInHistory;
import ru.pits.keywords.ccmportal.PacketConnect;
import ru.pits.keywords.crab.GettingProcessingPacketInfo;
import ru.pits.keywords.db.BasePacketSearch;
import ru.pits.keywords.db.SearchAbonentByStatusAndBalance;
import ru.pits.keywords.oapi.SearchingFreePacket;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**C266394: [Smoke] Добавление пакета услуг: Подключение бесплатного пакета без срока действия
 (время абонента = времени БД)*/
public class SmokeTest {

    SoftAssert asert = new SoftAssert();


    @Test
    public void testExecute() {
        /**1.1 Получаем Token*/
        String token = new GettingToken().getToken("","");

        /**2. Существует действующий клиент ФЛ с активным Абонентом*/
        /*выполняем keyword = "Поиск абонента в статусе {X} с балансом, превышающим {Y}"
        Входные параметры:
        Параметр Описание Значение по умолчанию
        CLIS_ID
        SBST_ID
        RTST_ID
        JRTP_ID
        CCAT_ID
        MACR_ID
        BALANCE

        Примечание: Если входной параметр не передан - использовать значение по умолчанию
        Запускаем со значениями по умолчанию
        */

        Map<Integer, Map<String, String>> activeClient = new SearchAbonentByStatusAndBalance().getResult();
        //запоминаем, что activeClient = это у нас {2} или {p2} в тестскрипте

        /** 3. Найден бесплатный пакет без заданного срока действия, доступный для подключения абоненту из
         предусловия #2*/

        /** 3.1 Выполнить keyword = "БД: Базовый поиск пакета*/
        /*
          Входные параметры:
          RECCURING_FLAG = 0
          DURATION_DAYS = 0
          DURATION_MONTHS = 0
        * */
        Map<String, String> baseSearchpParam = new HashMap<>();
        baseSearchpParam.put("RECCURING_FLAG", "0");
        baseSearchpParam.put("DURATION_DAYS", "0");
        baseSearchpParam.put("DURATION_MONTHS", "0");
        //Получаем и запоминаем выходные параметры

        Map<Integer, Map<String, String>> baseSearchresult = new BasePacketSearch(baseSearchpParam).getResult();
        //По тест кейсу нам нужен некий PackList, который не возвращается селектом. Поэтому будем использовать Pack_id
        //Что такое packList? на всякий случай отдам вообще все что нашел селект
        //TODO: уточнить что такое packList и дописать код в соответсвии с уточнениями, ане все что выдал селект

        /** 3.2. Выполнить keyword = "OAPI: Поиск бесплатного пакета для подключения"*/
        /*
        TOKEN (p1.1).{TOKEN}
        subscriberId (p2).{SUBS_ID}
        packsList (p3.1).{packsList}
        ps-timezone (p2).{TZNAME}*/

        Map<String, String> packIdandTZ = new SearchingFreePacket(token, activeClient, baseSearchresult).getPackIDAndTZMock();

        //т.к. у нас не все keywords, то считаем, что нужный пакет все-таки нашелся и мы получили его ИД
        //TODO: с появлением необходимых kewords дописать получение необходимых параметров

        /** 4.1. "CCM_Portal: Подключение пакета {X}".*/
        /* Входные данные
        TOKEN (p1.1).{TOKEN}
        subscriberId (p2).{SUBS_ID}
        packId (p3).{PACK_ID}
        ps-timezone (p2).{TZNAME}

        Выходные данные:
            orderId идентификатор заказа на подключение пакета
            subscriberPackId идентификатор экземпляра пакета

        */
        Date dateFrom = new Date().toString();
        Map<String, String > connectedPackData = new PacketConnect(token, packIdandTZ.get("subscriberId"), packIdandTZ.get("packID"), true, "1",
                dateFrom, packIdandTZ.get("psTimezone"), "1", ).getResult();

        /**4.2  Выполнить keyword = "CCM_Portal: Проверка заказов
         пакета в Истории заказов". */
        /*
            Входные Параметры:
            TOKEN (p1.1).{TOKEN}
            orderIds (1).{orderId}
            ps-timezone (p2).{TZNAME}


            Выходные параметры должны иметь значения
            orderStatusId 3
            orderTypeId 2
            crab_body.deactivationDate (p3).{DURATION_LIMIT_DATE}
            productInstanceId (1).{subscriberPackId}

            Зафиксировать:
            trace_number
            bisOrderId
            crab_body.activationDate
            crab_body.deactivationDate
            crab_body.productId - идентификатор продукта в CCM (ccm_pom.products)

        */

        Map<String, String> checkedPackorderInHistory = new CheckPacketOrderInHistory(token, connectedPackData.get("oredId"),
                packIdandTZ.get("subscriberId"), "", "", "", "",
                "", "", packIdandTZ.get("psTimezone")).getResult();

        /**проверки первые. наконец-то ;)*/
        asert.assertEquals(checkedPackorderInHistory.get("orderStatusId"), 3);
        asert.assertEquals(checkedPackorderInHistory.get("orderTypeId"), 2);
        asert.assertEquals(checkedPackorderInHistory.get("crab_body.deactivationDate"), 3);
        asert.assertEquals(checkedPackorderInHistory.get("DURATION_LIMIT_DATE"), "DURATION_LIMIT_DATE");

        /**4.3. Проверить состояние заказа на подключение пакета из предусловия 3 в CRAB.*/
        //Выполнить keyword = "CRAB: Получение информации об обработке заказа по пакету".
        Map<String, String> processedPackedInfo = new GettingProcessingPacketInfo("-").getResult();
        /**Проверки*/
        asert.assertEquals(processedPackedInfo.get("status.state"), "success");
        asert.assertEquals(processedPackedInfo.get("operation.nam.state"), "ccmPackActivate");
        asert.assertEquals(processedPackedInfo.get("subscriberPackId"), connectedPackData.get("subscriberPackId"));

        /**4.4. */





    }

}
