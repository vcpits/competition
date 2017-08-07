package ru.pits.keywords.ccmportal;

import ru.pits.keywords.oapi.GettingAbonentCharges;

import java.util.Map;

/**"CCM_Portal: Проверка свойств
 пакета услуг абонента*/

public class CheckAccrualList {

    private String token;
    private String msisdn;
    private String dateFrom;
    private String packIDs;

    public CheckAccrualList(String token, String msisdn, String dateFrom, String packIDs) {
        this.token = token;
        this.msisdn = msisdn;
        this.dateFrom = dateFrom;
        this.packIDs = packIDs;
    }

    /**Выполнить keyword = "OAPI: Получение списка начислений абонента (/charges/search)"*/
    public Map<String, String> getResult() {
        return new GettingAbonentCharges(this.token, this.msisdn, this.dateFrom, this.packIDs).getResult();
    }

}
