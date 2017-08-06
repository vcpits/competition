package ru.pits.utils;

import java.util.HashMap;
import java.util.Map;

public class ParametersBuilder {
    public static Map<String, String> build(Map<String, String> parameters){

        Map<String, String> result = new HashMap<>();

        for(Map.Entry<String, String> paramEntry : parameters.entrySet()){
            result.put(paramEntry.getKey(), paramEntry.getValue());
        }

        return result;
    }
}
