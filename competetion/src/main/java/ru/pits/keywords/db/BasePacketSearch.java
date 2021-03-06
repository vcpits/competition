package ru.pits.keywords.db;

import ru.pits.conector.DBConector;
import ru.pits.utils.ParseSqlResult;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**БД: Базовый поиск пакета*/

public class BasePacketSearch {


    //Входные значения. дефолтные
    private Map<String, String> params = new HashMap<>();


    //Запрос к БД
    private String sqlSelect;

    //Конструктор для запуска SQL с дефолтными значениями
    public BasePacketSearch() {
        setDefaultParams();
        setSqlSelect();
    }

    public BasePacketSearch(Map<String, String> params) {
        setDefaultParams();

        for(Map.Entry<String, String> paramEntry : params.entrySet())
            this.params.put(paramEntry.getKey(), paramEntry.getValue());

        setSqlSelect();
    }

    private void setDefaultParams() {
        this.params.put("RECCURING_FLAG", "2");
        this.params.put("DURATION_DAYS", "2");
        this.params.put("DURATION_MONTHS", "0");
        this.params.put("LIMIT_DATE", "30.12.2999");
    }

    private void setSqlSelect() {
        sqlSelect = "select p.pack_id,\n" +
                        "p.name_r AS PACK_NAME,\n" +
                        "p.duration_days,\n" +
                        "p.duration_months,\n" +
                        "p.duration_limit_date,\n" +
                        "p.recurring_flag\n" +
                        "from packs p\n" +
                        "where p.avail_date < sysdate + 1/86400 -- дата начала\n" +
                        "and p.expire_date >= sysdate + 300/86400 -- дата\n" +
                        "завершения выдачи пакета > текущей даты + 5 минут\n" +
                        "and nvl(p.duration_days,0) " + this.params.get("DURATION_DAYS") + " \n" +
                        "and nvl(p.duration_months,0)  " + this.params.get("DURATION_MONTHS") + " \n " +
                        "and (p.duration_limit_date  " + this.params.get("LIMIT_DATE") + " sysdate +\n" +
                        "300/86400 + nvl(p.duration_days,0) or\n" +
                        "p.duration_limit_date  " + this.params.get("LIMIT_DATE") + " sysdate + 300/86400 +\n" +
                        "nvl(p.duration_months,0)) -- максимальная дата действия\n" +
                        "and p.duration_limit_date  " + this.params.get("LIMIT_DATE") + " to_date\n" +
                        "('30.12.2999','DD.MM.YYYY') -- максимальная дата\n" +
                        "and nvl(p.recurring_flag,0)  " + this.params.get("RECCURING_FLAG") + " \n" +
                        "order by dbms_random.value";
    }

    private String getSqlSelect() {
        return sqlSelect;
    }

    private ResultSet executeSql() {
        return new DBConector().execute(getSqlSelect());
    }

    public Map<Integer, Map<String, String>> getResult() {
        ResultSet resultSet = new DBConector().execute(sqlSelect);
        return ParseSqlResult.execute(resultSet);
    }


}
