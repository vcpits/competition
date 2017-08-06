package ru.pits;

import org.testng.annotations.Test;
import ru.pits.keywords.GettingToken;


/**C266394: [Smoke] Добавление пакета услуг: Подключение бесплатного пакета без срока действия
 (время абонента = времени БД)*/
public class SmokeTest {
    @Test
    public void testExecute() {
        String token = (new GettingToken()).getToken();
    }

}
