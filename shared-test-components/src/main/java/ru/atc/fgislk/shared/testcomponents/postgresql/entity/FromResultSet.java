package ru.atc.fgislk.shared.testcomponents.postgresql.entity;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * интерфейс для таблиц, позволяет в шаред вынести методы получения данных из базовых таблиц
 */
public interface FromResultSet {
    /**
     * заполнить поля объекта значениями из БД
     * @param resultSet запись из БД
     */
    void fromResultSet(ResultSet resultSet) throws SQLException;
}
