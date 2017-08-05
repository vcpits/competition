package ru.pits.conector;

import ru.pits.utils.ConfigReader;

import java.util.Properties;

class DBProperties {
    private static final DBProperties instance = new DBProperties();
    private String driver;
    private String user;
    private String password;
    private String url;

    private DBProperties(){
        Properties properties = new Properties();
        ConfigReader configReader = new ConfigReader();
        configReader.read(properties);

        this.driver = properties.getProperty("db.driver");
        this.user = properties.getProperty("db.user");
        this.password = properties.getProperty("db.password");
        this.url = properties.getProperty("db.url");
    }

    static DBProperties getInstance() {
        return instance;
    }

    String getDriver() {
        return instance.driver;
    }

    String getUser() {
        return instance.user;
    }

    String getPassword() {
        return instance.password;
    }

    String getUrl() {
        return instance.url;
    }

}
