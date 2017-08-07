package ru.pits.keywords.db;

import ru.pits.conector.DBConector;
import ru.pits.utils.ParseSqlResult;

import java.sql.ResultSet;
import java.util.Map;

/**БД: Получение статуса пакета абонента в BIS
 */

public class GetAbonentPacketStatusBIS {

    private String subsId;
    private String packId;
    private String traceNumber;

    private String sqlSelect;


    public GetAbonentPacketStatusBIS(String subsId, String packId, String traceNumber) {
        this.packId = packId;
        this.subsId = subsId;
        this.traceNumber = traceNumber;

    }
    private void setSqlSelect() {
        this.sqlSelect = "SELECT\n" +
                "sp.subs_subs_id AS SUBS_ID,\n" +
                "sp.pack_pack_id AS PACK_ID,\n" +
                "p.name_r AS PACK_NAME,\n" +
                "sp.trace_number,\n" +
                "sp.start_date,\n" +
                "sp.end_date,\n" +
                "sp.navi_user,\n" +
                "sp.navi_date,\n" +
                "sp.prod_prod_id AS PROD_ID\n" +
                "FROM subs_packs sp\n" +
                "JOIN packs p ON p.pack_id=sp.pack_pack_id\n" +
                "WHERE sp.subs_subs_id = " + this.subsId + "\n" +
                "AND sp.pack_pack_id = " + this.packId + "\n" +
                "AND sp.trace_number = " + this.traceNumber;
    }
    //Считаем, что возвращается 1(ОДНА) строка
    public Map<String, String> getResult() {
        ResultSet resultSet = new DBConector().execute(sqlSelect);
        return ParseSqlResult.execute(resultSet).get("0");
    }
}
