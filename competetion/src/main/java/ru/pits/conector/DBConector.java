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
            //Выполним запрос
            //result это указатель на первую строку с выборки
            //чтобы вывести данные мы будем использовать
            //метод next() , с помощью которого переходим к следующему элементу
//            while (result.next()) {
//                System.out.println("Номер в выборке #" + result.getRow()
//                        + "\t Номер в базе #" + result.getInt("id")
//                        + "\t" + result.getString("username"));
//            }

            set = statement.executeQuery(reqest);

        } catch (Exception ex) {
            //выводим наиболее значимые сообщения
            log.error(ex.getMessage(), ex);
        }

        return set;
    }
}
