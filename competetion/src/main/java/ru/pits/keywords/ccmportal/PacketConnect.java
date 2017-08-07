package ru.pits.keywords.ccmportal;

import ru.pits.keywords.oapi.ActivatePack;

import java.util.Date;
import java.util.Map;

/**CCM_Portal: Подключение пакета  {X}*/
public class PacketConnect {

    /*
    TOKEN токен -
    subscriberId идентификатор абонента -
    packId идентификатор подключаемого пакета -
    checkBalance учет баланса (true/false) true
    accountTypeId идентификатор типа ЛС 1 - корпоративный ЛС
    activationDate планируемая дата подключения пакета не передано в запросе
    ps-timezone таймзона абонента не передано в запросе

    * */
    /**Входные данные */
    private String token;
    private String subscriberId;
    private String packID;
    private Boolean checkBalance;
    private String accountTypeId;
    private String activationDate;

    public String getToken() {
        return token;
    }

    public String getSubscriberId() {
        return subscriberId;
    }

    public String getPackID() {
        return packID;
    }

    public Boolean getCheckBalance() {
        return checkBalance;
    }

    public String getAccountTypeId() {
        return accountTypeId;
    }

    public String getActivationDate() {
        return activationDate;
    }

    public String getPsTimezone() {
        return psTimezone;
    }

    private String psTimezone;

    /**Выходные параметры*/
    String orderId;
    String subscriberPackId;

    public PacketConnect(String token, String subscriberId, String packID, Boolean checkBalance,
                         String accountTypeId, String accountTypeId, String activationDate) {
        this.token = token;
        this.subscriberId = subscriberId;
        this.packID = packID;
        this.checkBalance = checkBalance;
        this.accountTypeId = accountTypeId;
        this.activationDate = activationDate;
        this.psTimezone = psTimezone;
    }
    /**Вызов OAPI: Подключение пакета абоненту (/packs/activate)*
     * результаты вызова - наши выходные параметры
     */

    public Map<String, String> getResult() {
        return new ActivatePack(getToken(), getSubscriberId(), getPackID(), getCheckBalance(),
                getAccountTypeId(), getActivationDate(), getPsTimezone()).getResult();
    }

}
