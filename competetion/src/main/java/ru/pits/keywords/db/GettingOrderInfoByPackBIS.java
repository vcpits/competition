package ru.pits.keywords.db;

import ru.pits.conector.DBConector;
import ru.pits.utils.ParseSqlResult;

import java.sql.ResultSet;
import java.util.Map;

/**БД: Получение данных заказа BIS по пакету */

public class GettingOrderInfoByPackBIS {

    String bisOrderId;

    //Запрос к БД
    private String sqlSelect;

    public GettingOrderInfoByPackBIS(String bisOrderId) {
        this.bisOrderId = bisOrderId;
        setSqlSelect();
    }

    private void setSqlSelect() {
        this.sqlSelect = "SELECT so.subs_subs_id AS SUBS_ID,\n" +
                "so.sord_id,\n" +
                "sot.def AS ORDER_TYPE,\n" +
                "sot.sotp_id AS SOTP_ID,\n" +
                "sop.pack_pack_id AS PACK_ID,\n" +
                "sop.trace_number,\n" +
                "p.name_r AS PACK_NAME,\n" +
                "so.sost_sost_id AS SOST_ID,\n" +
                "sos.def STATUS_NAME,\n" +
                "so.start_date AS START_DATE,\n" +
                "so.end_date AS END_DATE\n" +
                "FROM subs_order_packs sop\n" +
                "JOIN subs_orders so on so.sord_id =\n" +
                "sop.sord_sord_id\n" +
                "JOIN bis.subs_order_statuses sos on\n" +
                "sos.sost_id=so.sost_sost_id\n" +
                "JOIN packs p on p.pack_id = sop.pack_pack_id\n" +
                "JOIN bis.subs_order_types sot on sot.sotp_id =\n" +
                "so.sotp_sotp_id\n" +
                "WHERE\n" +
                "so.sord_id = " + this.bisOrderId;
    }

    //Считаем, что возвращается 1(ОДНА) строка
    public Map<String, String> getResult() {
        ResultSet resultSet = new DBConector().execute(sqlSelect);
        return ParseSqlResult.execute(resultSet).get("0");
    }
}
