package ru.pits.keywords.db;

import ru.pits.conector.DBConector;
import ru.pits.utils.ParametersBuilder;
import ru.pits.utils.ParseSqlResult;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

/**Поиск абонента в статусе {X} с балансом, превышающим {Y}
 */

public class SearchAbonentByStatusAndBalance {

    //Входные значения. дефолтные
    private Map<String, String> params = new HashMap<>();

    //Запрос к БД
    private String sqlSelect;

    //Конструктор для запуска SQL с дефолтными значениями
    public SearchAbonentByStatusAndBalance() {
        setDefaultParams();
        setSqlSelect();

    }

    public SearchAbonentByStatusAndBalance(Map<String, String> params) {
        setDefaultParams();
        ParametersBuilder.build(params);
        setSqlSelect();
    }

    private void setDefaultParams() {
        this.params.put("CLIS_ID", "2");
        this.params.put("SBST_ID", "2");
        this.params.put("RTST_ID", "0");
        this.params.put("JRTP_ID", "1");
        this.params.put("CCAT_ID", "1");
        this.params.put("MACR_ID", "200");
        this.params.put("BALANCE", "0");
    }

    private void setSqlSelect() {
        this.sqlSelect = "SELECT ch.brnc_brnc_id AS BRNC_ID, " +
                "c.clnt_id, " +
                "s.subs_id, " +
                "ns.msisdn, " +
                "sc.imsi, " +
                "sh.rtpl_rtpl_id AS RTPL_ID, " +
                "rp.name_r, " +
                "-b.balance_$ AS BALANCE, " +
                "ro.rmop_id, " +
                "cc.def, " +
                "ro.macr_macr_id AS MACR_ID, " +
                "tr.tfrg_id, " +
                "s.curr_zone_id AS ZONE_ID, " +
                "z.tzname " +
                "FROM subscribers s " +
                "JOIN client_histories ch ON ch.clnt_clnt_id = " +
                "s.clnt_clnt_id " +
                "JOIN clients c ON c.clnt_id = ch.clnt_clnt_id " +
                "JOIN subs_histories sh ON sh.subs_subs_id = " +
                "s.subs_id " +
                "JOIN sim_cards sc ON sh.scrd_scrd_id = sc.scrd_id " +
                "JOIN bis.subs_lc_histories slh ON slh.subs_subs_id = " +
                "s.subs_id " +
                "JOIN phone_histories ph ON ph.subs_subs_id = " +
                "s.subs_id " +
                "JOIN number_sets ns ON ph.nset_nset_id = ns.nset_id " +
                "JOIN balances b ON b.clnt_clnt_id = s.clnt_clnt_id " +
                "JOIN rate_plans rp ON rp.rtpl_id = sh.rtpl_rtpl_id " +
                "JOIN zones z ON z.zone_id = s.curr_zone_id " +
                "JOIN tarification_regions tr ON tr.tfrg_id = " +
                "z.tfrg_tfrg_id " +
                "JOIN roam_operators ro ON ro.macr_macr_id = " +
                "tr.macr_macr_id " +
                "JOIN branches bra ON ch.brnc_brnc_id = bra.brnc_id " +
                "JOIN client_cats cc ON ch.ccat_ccat_id = cc.ccat_id " +
                "WHERE ch.end_date > SYSDATE + 1/86400 and ch.start_date " +
                "< SYSDATE + 1/86400 " +
                "AND sh.end_date > SYSDATE + 1/86400 and " +
                "sh.start_date < SYSDATE + 1/86400 " +
                "AND slh.end_date > SYSDATE + 1/86400 and " +
                "slh.start_date < SYSDATE + 1/86400 " +
                "AND ph.end_date > SYSDATE + 1/86400 and " +
                "ph.start_date < SYSDATE + 1/86400 " +
                "AND rp.end_date > SYSDATE + 1/86400 and " +
                "rp.start_date < SYSDATE + 1/86400 " +
                "AND ch.clis_clis_id = " + this.params.get("CLIS_ID") + " " +
                "AND s.sbst_sbst_id = " + this.params.get("SBST_ID") + " " +
                "AND s.stnd_stnd_id = 1" +
                "AND ph.ncat_ncat_id = 1 а " +
                "AND slh.rtst_rtst_id = " + this.params.get("RTST_ID") + " " +
                "AND c.jrtp_jrtp_id = " + this.params.get("JRTP_ID") + " " +
                "AND ch.ccat_ccat_id IN (" + this.params.get("CCAT_ID") + ") " +
                "AND s.macr_macr_id = " + this.params.get("MACR_ID") + " " +
                "AND bra.macr_macr_id = ro.macr_macr_id " +
                "AND -b.balance_$ > " + this.params.get("BALANCE") + " " +
                "order by -b.balance_$ desc";
    }

    public Map<Integer, Map<String, String>> getResult() {
        ResultSet resultSet = new DBConector().execute(sqlSelect);
        return ParseSqlResult.execute(resultSet);
    }


}
