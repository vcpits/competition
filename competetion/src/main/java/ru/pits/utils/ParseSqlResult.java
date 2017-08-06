package ru.pits.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class ParseSqlResult {
    public static Map<Integer, Map<String, String>> execute(ResultSet resultSet){
        Logger log = LoggerFactory.getLogger(ParseSqlResult.class);

        Map<Integer, Map<String, String>> result = new HashMap<>();

        try {
            while(resultSet.next()) {
                Map<String, String > resultMap = new HashMap<>();
                for (int i = 1; i < resultSet.getMetaData().getColumnCount(); i++) {
                    resultMap.put(resultSet.getMetaData().getColumnName(i), resultSet.getString(i));
                }
                result.put(resultSet.getRow(), resultMap);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return result;
    }

}
