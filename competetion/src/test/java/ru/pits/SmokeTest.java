package ru.pits;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.pits.keywords.GettingToken;
import ru.pits.keywords.brt.GetAbonentPackData;
import ru.pits.keywords.brt.GetTelnetPort;
import ru.pits.keywords.cart.WriteOffObject;
import ru.pits.keywords.ccmportal.CheckAbonentPackProperties;
import ru.pits.keywords.ccmportal.CheckAccrualList;
import ru.pits.keywords.ccmportal.CheckPacketOrderInHistory;
import ru.pits.keywords.ccmportal.PacketConnect;
import ru.pits.keywords.crab.GettingProcessingPacketInfo;
import ru.pits.keywords.db.BasePacketSearch;
import ru.pits.keywords.db.GetAbonentPacketStatusBIS;
import ru.pits.keywords.db.GettingOrderInfoByPackBIS;
import ru.pits.keywords.db.SearchAbonentByStatusAndBalance;
import ru.pits.keywords.oapi.GettingAbonentPackHistory;
import ru.pits.keywords.oapi.SearchingFreePacket;
import ru.pits.keywords.ufm.UploadingClientData;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**C266394: [Smoke] Добавление пакета услуг: Подключение бесплатного пакета без срока действия
 (время абонента = времени БД)*/
public class SmokeTest {

    SoftAssert asert = new SoftAssert();


    @Test
    public void testExecute() throws ParseException {
        /**1.1 Получаем Token*/
        String token = new GettingToken().getToken("","");

        /**2. Существует действующий клиент ФЛ с активным Абонентом*/
        Map<Integer, Map<String, String>> activeClient = new SearchAbonentByStatusAndBalance().getResult();

        /** 3. Найден бесплатный пакет без заданного срока действия, доступный для подключения абоненту из
         предусловия #2*/

        /** 3.1 Выполнить keyword = "БД: Базовый поиск пакета*/

        Map<String, String> baseSearchpParam = new HashMap<>();
        baseSearchpParam.put("RECCURING_FLAG", "0");
        baseSearchpParam.put("DURATION_DAYS", "0");
        baseSearchpParam.put("DURATION_MONTHS", "0");
        //Получаем и запоминаем выходные параметры

        Map<Integer, Map<String, String>> baseSearchResult = new BasePacketSearch(baseSearchpParam).getResult();
        //По тест кейсу нам нужен некий PackList, который не возвращается селектом. Поэтому будем использовать Pack_id
        //Что такое packList? на всякий случай отдам вообще все что нашел селект
        //TODO: уточнить что такое packList и дописать код в соответсвии с уточнениями, ане все что выдал селект.

        /** 3.2. Выполнить keyword = "OAPI: Поиск бесплатного пакета для подключения"*/
         Map<String, String> packIdandTZ = new SearchingFreePacket(token, activeClient, baseSearchResult).getPackIDAndTZMock();

        //т.к. у нас не все keywords, то считаем, что нужный пакет все-таки нашелся и мы получили его ИД
        //TODO: с появлением необходимых kewords дописать получение необходимых параметров

        /** 4.1. "CCM_Portal: Подключение пакета {X}".*/

        String dateFrom = new Date().toString();
        Map<String, String > connectedPackData = new PacketConnect(token, packIdandTZ.get("subscriberId"), packIdandTZ.get("packID"),
                true, "1", dateFrom, packIdandTZ.get("psTimezone")).getResult();


        /**4.2  Выполнить keyword = "CCM_Portal: Проверка заказов
         пакета в Истории заказов". */

        Map<String, String> checkedPackOrderInHistory = new CheckPacketOrderInHistory(token, connectedPackData.get("oredId"),
                packIdandTZ.get("subscriberId"), "", "", "", "",
                "", "", packIdandTZ.get("psTimezone")).getResult();

        /**проверки первые. наконец-то ;)*/
        asert.assertEquals(checkedPackOrderInHistory.get("orderStatusId"), 3);
        asert.assertEquals(checkedPackOrderInHistory.get("orderTypeId"), 2);
        asert.assertEquals(checkedPackOrderInHistory.get("crab_body.deactivationDate"), 3);
        asert.assertEquals(checkedPackOrderInHistory.get("DURATION_LIMIT_DATE"), "DURATION_LIMIT_DATE");

        /**4.3. Проверить состояние заказа на подключение пакета из предусловия 3 в CRAB.*/
        //Выполнить keyword = "CRAB: Получение информации об обработке заказа по пакету".
        Map<String, String> processedPackedInfo = new GettingProcessingPacketInfo(packIdandTZ.get("packID")).getResult();

        /**Проверки*/
        asert.assertEquals(processedPackedInfo.get("status.state"), "success");
        asert.assertEquals(processedPackedInfo.get("operation.nam.state"), "ccmPackActivate");
        asert.assertEquals(processedPackedInfo.get("subscriberPackId"), connectedPackData.get("subscriberPackId"));

        /**4.4. Проверить состояние заказа на подключение пакета из предусловия 3 в БД BIS.*/
        //Выполнить keyword = "БД: Получение данных заказа BIS по пакету".

        Map<String, String> orderInfoByPackBis = new GettingOrderInfoByPackBIS(checkedPackOrderInHistory.get("bisOrderId")).getResult();

        /**CheckLists*/
        asert.assertEquals(orderInfoByPackBis.get("SUBS_ID"), packIdandTZ.get("SUBS_ID"));
        asert.assertEquals(orderInfoByPackBis.get("PACK_ID"), packIdandTZ.get("packID"));
        asert.assertEquals(orderInfoByPackBis.get("TRACE_NUMBER"), checkedPackOrderInHistory.get("trace_number"));
        asert.assertEquals(orderInfoByPackBis.get("END_DATE"), checkedPackOrderInHistory.get("crab_body.deactivationDate"));

        /**4.5. Проверить данные по пакету из предусловия 3 в списке пакетов абонента.*/
        //Выполнить keyword = "CCM_Portal: Проверка свойств пакета услуг абонента".

        Map<String, Map<String, String>> abonentPAckProperties = new CheckAbonentPackProperties(token, packIdandTZ.get("SUBS_ID"), connectedPackData.get("subscriberPackId"),
                packIdandTZ.get("packID"), packIdandTZ.get("psTimezone")).getResult();

        /**проверки из пакета "CCM_Portal: Проверка свойств пакета услуг абонента"*/
        asert.assertEquals(abonentPAckProperties.get("result1").get("packStatusId"), abonentPAckProperties.get("result1").get("packStatusId"));
        asert.assertEquals(abonentPAckProperties.get("result1").get("activationDate"), abonentPAckProperties.get("result1").get("activationDate"));
        asert.assertEquals(abonentPAckProperties.get("result1").get("deactivationDate"), abonentPAckProperties.get("result1").get("deactivationDate"));
        asert.assertEquals(abonentPAckProperties.get("result1").get("accountTypeId"), abonentPAckProperties.get("result1").get("accountTypeId"));
        /** проверки SmokeTest*/
        asert.assertEquals(abonentPAckProperties.get("result1").get("packStatusId"), "1");
        asert.assertEquals(abonentPAckProperties.get("result1").get("activationDate"), checkedPackOrderInHistory.get("activationDate"));
        asert.assertEquals(abonentPAckProperties.get("result1").get("deactivationDate"), checkedPackOrderInHistory.get("deactivationDate"));

        /**4.6. Выполнить keyword = "OAPI: Получение истории по пакету абонента (/packs/{packId}/history)".*/
        Map<String, String> abonentPackHistory = new GettingAbonentPackHistory(token, packIdandTZ.get("subscriberId"), packIdandTZ.get("packID"),
                checkedPackOrderInHistory.get("activationDate"), checkedPackOrderInHistory.get("deactivationDate"), "1", packIdandTZ.get("psTimezone")).getResult();

        asert.assertEquals(abonentPackHistory.get("activationDate"), checkedPackOrderInHistory.get("activationDate}"));
        asert.assertEquals(abonentPackHistory.get("deactivationDate"), checkedPackOrderInHistory.get("deactivationDate}"));
        asert.assertEquals(abonentPackHistory.get("status.packStatusId"), "1");

        /**4.7. Выполнить keyword = "БД: Получение статуса пакета абонента в BIS".*/
        Map<String, String> abonentPacketStatusBIS = new GetAbonentPacketStatusBIS(packIdandTZ.get("subscriberId"), packIdandTZ.get("packID"),
                checkedPackOrderInHistory.get("TRACE_NUMBER")).getResult();

        /**Проверки*/
        asert.assertEquals(abonentPacketStatusBIS.get("PROD_ID"), checkedPackOrderInHistory.get("crab_body.productId"));
        asert.assertEquals(abonentPacketStatusBIS.get("START_DATE"), checkedPackOrderInHistory.get("activationDate"));
        asert.assertEquals(abonentPacketStatusBIS.get("END_DATE"), checkedPackOrderInHistory.get("deactivationDate"));

        /**4.8. Выполнить keyword = "CART: Объект списания абонента".*/
        String writeOff = new WriteOffObject("5", packIdandTZ.get("subscriberId"), packIdandTZ.get("packID"), "3").getResult();
        asert.assertTrue(writeOff.equals(""));//проверка что ничего не "нашлось".


        /**4.9. Выполнить keyword = "CCM_Portal: Проверка списка начислений"*/
        String startDay = "0";// начало текущих суток. В каком вид они не говорится. поэтому просто заглушка. типа есть.
        Map<String, String> accrualList = new CheckAccrualList(token, packIdandTZ.get("packID"), startDay, packIdandTZ.get("packID" )).getResult();

        /**CheckList*/
        asert.assertEquals(accrualList.get("count"), "0");


        /**4.10 Выполнить keyword = "BRT: Получение данных по пакетам абонента"*/
        DateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzzz yyyy");
        Date date1 = new Date();
        Date date2 = new Date();

        Map<String, String> abonentPackData = null;
        try {
            String port = new GetTelnetPort().getPort();
            abonentPackData = new GetAbonentPackData(port, packIdandTZ.get("subscriberId")).getResult();
            asert.assertEquals(abonentPackData.get("subs"),  packIdandTZ.get("subscriberId"));
            asert.assertEquals(abonentPackData.get("pack"),  packIdandTZ.get("packID"));
            asert.assertEquals(abonentPackData.get("trace_number"),  checkedPackOrderInHistory.get("trace_number"));
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            date1 = format.parse(abonentPackData.get("start"));
            date2 = format.parse(checkedPackOrderInHistory.get("activationDate"));
            asert.assertTrue(date1.getTime() - date2.getTime() < 10); //пускай 10 - критерий незначительности
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            date1 = format.parse(abonentPackData.get("end"));
            date2 = format.parse(checkedPackOrderInHistory.get("deactivationDate"));
            asert.assertTrue(date1.getTime() - date2.getTime() <10);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        /**4.11UFM: Выгрузка данных по клиенту*/
        Map<String, String> clientActivePacks = new UploadingClientData("-msisdn ", packIdandTZ.get("subscriberId")).getResult();

        /**CheckList*/
        asert.assertEquals(clientActivePacks.get("objId"),  packIdandTZ.get("packID"));
        asert.assertEquals(clientActivePacks.get("SUBS_ID"),  packIdandTZ.get("subsId"));
        asert.assertEquals(clientActivePacks.get("PACK_ID"),  packIdandTZ.get("packID"));
        asert.assertEquals(clientActivePacks.get("MSISDN"),  packIdandTZ.get("msidn"));

        try {
            date1 = format.parse(clientActivePacks.get("actualDate"));
            date2 = format.parse(packIdandTZ.get("activationDate"));

            asert.assertEquals(date1.compareTo(date2), 1 );
        } catch (ParseException e) {
            e.printStackTrace();
        }


        asert.assertAll();
    }



}
