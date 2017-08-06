package ru.pits;

import org.testng.annotations.Test;
import ru.pits.keywords.GettingToken;
import ru.pits.keywords.db.BasePacketSearch;
import ru.pits.keywords.db.SearchAbonentByStatusAndBalance;
import ru.pits.keywords.oapi.SearchingFreePacket;

import java.util.HashMap;
import java.util.Map;

/**C266394: [Smoke] Добавление пакета услуг: Подключение бесплатного пакета без срока действия
 (время абонента = времени БД)*/
public class SmokeTest {



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
        Map<String, String> baseSearchpParams = new HashMap<>();
        baseSearchpParams.put("RECCURING_FLAG", "0");
        baseSearchpParams.put("DURATION_DAYS", "0");
        baseSearchpParams.put("DURATION_MONTHS", "0");
        //Получаем и запоминаем выходные параметры

        Map<String, String> baseSearchresult = new BasePacketSearch(baseSearchpParams).getResult();
        //По тест кейсу нам нужен некий PackList, который не возвращается селектом. Поэтому будем использовать Pack_id
        //Что такое packList?

        /** Выполнить keyword = "OAPI: Поиск бесплатного пакета для подключения"*/
        String packId = new SearchingFreePacket().getPackID();
    }

}
