package ru.pits.conector;

import org.slf4j.*;

import java.sql.*;

public class DBConector {
    Logger log = LoggerFactory.getLogger(DBConector.class);

    public ResultSet execute(String reqest) {

        DBProperties properties = DBProperties.getInstance();

        ResultSet set = null;

        try {
            //Загружаем драйвер
            Class.forName(properties.getDriver());
            log.info("Драйвер подключен");
            //Создаём соединение
            Connection connection = DriverManager.getConnection(
                    properties.getUrl(),
                    properties.getUser(),
                    properties.getPassword());
            log.info("Соединение установлено");
            Statement statement = connection.createStatement();
            set = statement.executeQuery(reqest);

        } catch (Exception ex) {
            //выводим наиболее значимые сообщения
            log.error(ex.getMessage(), ex);
        }

        return set;
    }
}
