package ru.pits;

import org.testng.annotations.Test;
import ru.pits.keywords.GettingToken;
import ru.pits.keywords.db.SearchAbonentByStatusAndBalance;

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

        Map<String, String> activeClient = new SearchAbonentByStatusAndBalance().getResult();
        //запоминаем, что activeClient = это у нас {2} или {p2} в тестскрипте



    }

}
