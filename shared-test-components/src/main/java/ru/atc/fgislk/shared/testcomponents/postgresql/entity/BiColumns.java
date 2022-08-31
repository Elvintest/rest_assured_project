package ru.atc.fgislk.shared.testcomponents.postgresql.entity;

import io.qameta.allure.Step;
import org.testng.Assert;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;

/**
 * Стандартные поля по требованимя BI. не должны быть пустыми
 */
public class BiColumns implements FromResultSet {
    /**
     * Дата время вставки записи
     */
    protected OffsetDateTime createDttm;
    /**
     * Дата время последнего изменения записи
     */
    protected OffsetDateTime modifyDttm;
    /**
     * Индикатор действия
     */
    protected String actionInd;

    public BiColumns() {
    }

    public BiColumns(ResultSet resultSet) throws SQLException {
        resultSet.getTimestamp("create_dttm");
        if (resultSet.wasNull())
            createDttm = null;
        else
            createDttm = OffsetDateTime.ofInstant(resultSet.getTimestamp("create_dttm").toInstant(), ZoneId.systemDefault());
        resultSet.getTimestamp("modify_dttm");
        if (resultSet.wasNull())
            modifyDttm = null;
        else
            modifyDttm = OffsetDateTime.ofInstant(resultSet.getTimestamp("modify_dttm").toInstant(), ZoneId.systemDefault());
        actionInd = resultSet.getString("action_ind");
    }

    /**
     * Проверка полей BI на то, что они не пустые. Значения не важны
     */
    @Step("проверка полей BI")
    public void checkBI(String... excludeColumns) {
        List<String> excludeList = Arrays.asList(excludeColumns);
        if (!excludeList.contains("create_dttm"))
            Assert.assertNotNull(createDttm, "значение create_dttm не должно быть NULL");
        if (!excludeList.contains("modify_dttm"))
            Assert.assertNotNull(modifyDttm, "значение modify_dttm не должно быть NULL");
        if (!excludeList.contains("action_ind"))
            Assert.assertNotNull(actionInd, "значение action_ind не должно быть NULL");
    }

    @Override
    public void fromResultSet(ResultSet resultSet) throws SQLException {
        resultSet.getTimestamp("create_dttm");
        if (resultSet.wasNull())
            createDttm = null;
        else
            createDttm = OffsetDateTime.ofInstant(resultSet.getTimestamp("create_dttm").toInstant(), ZoneId.systemDefault());
        resultSet.getTimestamp("modify_dttm");
        if (resultSet.wasNull())
            modifyDttm = null;
        else
            modifyDttm = OffsetDateTime.ofInstant(resultSet.getTimestamp("modify_dttm").toInstant(), ZoneId.systemDefault());
        actionInd = resultSet.getString("action_ind");
    }

    public OffsetDateTime getCreateDttm() {
        return createDttm;
    }

    public OffsetDateTime getModifyDttm() {
        return modifyDttm;
    }

    public String getActionInd() {
        return actionInd;
    }

    public void setCreateDttm(OffsetDateTime createDttm) {
        this.createDttm = createDttm;
    }

    public void setModifyDttm(OffsetDateTime modifyDttm) {
        this.modifyDttm = modifyDttm;
    }

    public void setActionInd(String actionInd) {
        this.actionInd = actionInd;
    }
}
