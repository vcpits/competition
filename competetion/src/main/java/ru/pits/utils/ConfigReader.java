package ru.pits.utils;

import java.io.*;
import java.util.Properties;

public class ConfigReader {

    public void read(Properties properties) {
        try(InputStream stream = getClass().getClassLoader().getResourceAsStream("connection.properties")){
            properties.load(stream);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
