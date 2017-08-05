package ru.pits.conector;

import org.slf4j.*;

import java.sql.*;

public class DBConector {
    Logger log = LoggerFactory.getLogger(DBConector.class);

    public void execute() {

        DBProperties properties = DBProperties.getInstance();

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
            ResultSet result = statement.executeQuery(
                    "SELECT * FROM users where id >2 and id <10");
            //result это указатель на первую строку с выборки
            //чтобы вывести данные мы будем использовать
            //метод next() , с помощью которого переходим к следующему элементу
            while (result.next()) {
                System.out.println("Номер в выборке #" + result.getRow()
                        + "\t Номер в базе #" + result.getInt("id")
                        + "\t" + result.getString("username"));
            }
            // Вставить запись
//            statement.executeUpdate(
//                    "INSERT INTO users(username) values('name')");
            //Обновить запись
//            statement.executeUpdate(
//                    "UPDATE users SET username = 'admin' where id = 1");
        } catch (Exception ex) {
            //выводим наиболее значимые сообщения
            log.error(ex.getMessage(), ex);
        }

    }
}
