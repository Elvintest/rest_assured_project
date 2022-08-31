package ru.atc.fgislk.shared.testcomponents.postgresql;

import io.qameta.allure.Allure;
import org.testng.Assert;
import org.testng.util.Strings;
import ru.atc.fgislk.shared.testcomponents.postgresql.entity.FromResultSet;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

/**
 * Сервис работы с БД
 */
public class PostgreSQL {
    private static final String REQUEST_IN_DB = "Запрос в БД";

    /**
     * Таймаут ожидания результатов запроса. Очень часто поиск результата происходит до того, как бэк сложит все в БД
     */
    private int waitResultTimeout = 30;
    /**
     * задержка между повторным запросом, в миллисекундах
     */
    private static final int SLEEP = 1000;
    /**
     * Путь к БД
     */
    protected String pathBD;
    /**
     * Логин пользователя
     */
    protected String login;
    /**
     * Пароль пользователя
     */
    protected String password;
    protected Connection connection;
    Statement statement;

    public PostgreSQL(String pathBD, String login, String password) throws SQLException {
        this.pathBD = pathBD;
        this.login = login;
        this.password = password;

        connection = DriverManager.getConnection(pathBD, login, password);
        connection.setAutoCommit(false);

        statement = null;
        statement = connection.createStatement();
    }

    /**
     * Подключение к БД с указанием стенда и схемы
     * @param pathBD путь к БД
     * @param login логин
     * @param password пароль
     * @param schema схема
     */
    public PostgreSQL(String pathBD, String login, String password, String schema) throws SQLException {
        this(pathBD + "?currentSchema=" + schema, login, password);
    }

    /**
     * Закрыть соединение с БД
     */
    public void close() throws SQLException {
        statement.close();
        connection.close();
    }

    /**
     * Выполнить запрос SELECT и вернуть результат.
     * Ждет пока запрос не вернет что-нибудь в течении времени задержки, задаваемого параметром
     * Без логгирования запроса в Аллюр.
     *
     * @param query             запрос SELECT`
     * @param waitTimeInSeconds время ожидания результата в секундах
     * @return результат
     */
    protected ResultSet execQueryNoLog(String query, long waitTimeInSeconds) throws SQLException, InterruptedException {
        return execQuery(query, waitTimeInSeconds, null);
    }

    /**
     * Выполнить запрос SELECT и вернуть результат.
     * Ждет пока запрос не вернет что-нибудь в тении стандартного времени задержки.
     * Запрос логгиреуется в Аллюр.
     *
     * @param query запрос SELECT`
     * @return результат
     */
    protected ResultSet execQuery(String query) throws SQLException, InterruptedException {
        return execQuery(query, REQUEST_IN_DB);
    }

    /**
     * Выполнить запрос SELECT и вернуть результат.
     * Ждет пока запрос не вернет что-нибудь в тении стандартного времени задержки.
     * Запрос логгиреуется в Аллюр.
     *
     * @param query      запрос SELECT
     * @param allureName название для сохранения запроса в лог Аллюр
     * @return результат
     */
    protected ResultSet execQuery(String query, String allureName) throws SQLException, InterruptedException {
        return execQuery(query, waitResultTimeout, allureName);
    }

    /**
     * Выполнить запрос SELECT и вернуть результат.
     * Ждет пока запрос не вернет что-нибудь в течении времени задержки.
     * Запрос логгиреуется в Аллюр.
     *
     * @param query             запрос SELECT`
     * @param waitTimeInSeconds время ожидания результата в секундах
     * @return результат
     */
    protected ResultSet execQuery(String query, long waitTimeInSeconds) throws SQLException, InterruptedException {
        return execQuery(query, waitTimeInSeconds, REQUEST_IN_DB);
    }

    /**
     * Выполнить запрос SELECT и вернуть результат.
     * Ждет пока запрос не вернет что-нибудь в течении времени задержки.
     * Запрос логгиреуется в Аллюр.
     *
     * @param query             запрос SELECT`
     * @param waitTimeInSeconds время ожидания результата в секундах
     * @param allureName        название для сохранения запроса в лог Аллюр
     * @return результат
     */
    protected ResultSet execQuery(String query, long waitTimeInSeconds, String allureName) throws SQLException, InterruptedException {
        queryToAllureLog(query, allureName);
        ResultSet rs = statement.executeQuery(query);
        LocalDateTime stopTime = LocalDateTime.now().plusSeconds(waitTimeInSeconds);
        // пока не настиупило время таймаута и нет результатов
        while (LocalDateTime.now().isBefore(stopTime) && !rs.isBeforeFirst()) {
            rs = statement.executeQuery(query);
            Thread.sleep(SLEEP);
        }
        return rs;
    }

    /**
     * сохранить запрос в лог Аллюр
     *
     * @param query   запрос
     * @param logName название. Если срока пустая, то запрос не логгируется
     */
    private void queryToAllureLog(String query, String logName) {
        if (!Strings.isNullOrEmpty(logName))
            Allure.attachment(logName, query);
    }

    /**
     * получить одну запись из таблицы
     * Без логгирования в Аллюр.
     *
     * @param clazz класс типа
     * @param query запрос
     * @param <T>   тип
     * @return одна запись Т
     */
    protected <T extends FromResultSet> T getOneRecordNoLog(Class<T> clazz, String query) throws SQLException, IllegalAccessException, InstantiationException, InterruptedException {
        List<T> res = getRecordsListNoLog(clazz, query);
        Assert.assertTrue(res.size() < 2, "Запрос в БД вернул более 1 записи");
        return res.isEmpty() ? null : res.get(0);
    }

    /**
     * получить одну запись из таблицы
     * Запрос логгиреуется в Аллюр.
     *
     * @param clazz      класс типа
     * @param query      запрос
     * @param allureName название для сохранения запроса в лог Аллюр
     * @param <T>        тип
     * @return одна запись Т
     */
    protected <T extends FromResultSet> T getOneRecord(Class<T> clazz, String query, String allureName) throws SQLException, IllegalAccessException, InstantiationException, InterruptedException {
        List<T> res = getRecordsList(clazz, query, allureName);
        Assert.assertTrue(res.size() < 2, "Запрос в БД вернул более 1 записи");
        return res.isEmpty() ? null : res.get(0);
    }

    /**
     * Получить список записей
     * Без логгирования в Аллюр
     *
     * @param clazz класс типа
     * @param query запрос
     * @param <T>   тип
     * @return список записей List T
     */
    protected <T extends FromResultSet> List<T> getRecordsListNoLog(Class<T> clazz, String query) throws SQLException, InterruptedException, IllegalAccessException, InstantiationException {
        return getRecordsList(clazz, query, null);
    }

    /**
     * получить список записей.
     * Запрос логгируется в Аллюр.
     *
     * @param clazz класс типа
     * @param query запрос
     * @param <T>   тип
     * @return список записей List T
     */
    protected <T extends FromResultSet> List<T> getRecordsList(Class<T> clazz, String query) throws SQLException, InterruptedException, IllegalAccessException, InstantiationException {
        return getRecordsList(clazz, query, REQUEST_IN_DB);
    }

    /**
     * получить список записей, запрос логгируется в Аллюр
     *
     * @param clazz      класс типа
     * @param query      запрос
     * @param allureName название для сохранения запроса в лог Аллюр
     * @param <T>        тип
     * @return список записей List T
     */
    protected <T extends FromResultSet> List<T> getRecordsList(Class<T> clazz, String query, String allureName) throws SQLException, InterruptedException, IllegalAccessException, InstantiationException {
        ResultSet resultSet = execQuery(query, allureName);
        return getListFromResultSet(clazz, resultSet);
    }

    /**
     * Сформировать из ResultSet список записей типа
     *
     * @param clazz     класс типа
     * @param resultSet источник для формирования списка
     * @param <T>       тип
     * @return список записей
     */
    private <T extends FromResultSet> List<T> getListFromResultSet(Class<T> clazz, ResultSet resultSet) throws IllegalAccessException, InstantiationException, SQLException{
        List<T> resList = new ArrayList<>();
        while (resultSet.next()) {
            try {
                Constructor c = clazz.getConstructor();
                T instance = (T) c.newInstance();
                instance.fromResultSet(resultSet);
                resList.add(instance);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }  catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }

        }
        return resList;

    }

    /**
     * Получить значение таймаута ожидания результатов запроса
     *
     * @return значение таймаута
     */
    public int getWaitResultTimeout() {
        return waitResultTimeout;
    }

    /**
     * Установить значение таймаута ожидания результатов запроса, в секундах
     *
     * @param waitResultTimeout значение таймаута в секундах
     */
    public void setWaitResultTimeout(int waitResultTimeout) {
        this.waitResultTimeout = waitResultTimeout;
    }

    /**
     * Проверить строковое значение. Если значение null, то проверка не проводится
     *
     * @param rs    - строка БД
     * @param name  - название столбца
     * @param value - значение
     */
    public void checkString(ResultSet rs, String name, String value) throws SQLException {
        if (value == null)
            return;
        Assert.assertEquals(rs.getString(name), value, "Значение в столбце " + name + " не совпадает с ожидаемым");
    }

    /**
     * Проверить целое значение. Если значение null, то проверка не проводится
     *
     * @param rs    - строка БД
     * @param name  - название столбца
     * @param value - значение
     */
    public void checkLong(ResultSet rs, String name, Long value) throws SQLException {
        if (value == null)
            return;
        Assert.assertEquals(rs.getLong(name), value.longValue(), "Значение в столбце " + name + " не совпадает с ожидаемым");
    }

    /**
     * Проверить OffsetDateTime. Если значение null, то проверка не проводится
     *
     * @param rs    - строка БД
     * @param name  - название столбца
     * @param value - значение
     */
    public void checkZonedDateTime(ResultSet rs, String name, OffsetDateTime value) throws SQLException {
        if (value == null)
            return;
        OffsetDateTime zdt = OffsetDateTime.ofInstant(rs.getTimestamp(name).toInstant(), ZoneId.systemDefault());
        Assert.assertEquals(zdt, value, "Значение в столбце " + name + " не совпадает с ожидаемым");
    }

    /**
     * Проверить логическое значение. Если значение null, то проверка не проводится
     *
     * @param rs    - строка БД
     * @param name  - название столбца
     * @param value - значение
     */
    public void checkBoolean(ResultSet rs, String name, Boolean value) throws SQLException {
        if (value == null)
            return;
        Assert.assertEquals(rs.getBoolean(name), value.booleanValue(), "Значение в столбце " + name + " не совпадает с ожидаемым");
    }
}
