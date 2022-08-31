package ru.atc.fgislk.shared.testcomponents.back;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.internal.collections.Pair;
import ru.atc.fgislk.shared.testcomponents.back.dto.AND;
import ru.atc.fgislk.shared.testcomponents.back.dto.Filter;
import ru.atc.fgislk.shared.testcomponents.back.dto.NSIAttributeName;
import ru.atc.fgislk.shared.testcomponents.back.dto.NSIrecordId;
import ru.atc.fgislk.shared.testcomponents.back.health.Health;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static ru.atc.fgislk.shared.testcomponents.Util.GSON;
import static ru.atc.fgislk.shared.testcomponents.Util.getAny;

public class CommonBackMethod extends BaseRestService {
    public CommonBackMethod(String apiUrl) {
        super(apiUrl);
    }

    /**
     * Проверить работоспособность сервиса
     *
     * @return Пара код ответа, текст ответа
     */
    public Pair<Integer, String> health() {
        return get("/actuator/health", "проверить статус сервиса");
    }

    /**
     * liveness-probe
     *
     * @return Пара код ответа, текст ответа
     */
    public Pair<Integer, String> liveness() {
        return get("/actuator/health/liveness", "проверить \"живость\" сервиса");
    }

    /**
     * readiness-probe
     *
     * @return Пара код ответа, текст ответа
     */
    public Pair<Integer, String> readiness() {
        return get("/actuator/health/readiness", "проверить готовность сервиса");
    }

    /**
     * шаг теста выполнить проверку health
     */
    @Step("проверка /actuator/health")
    public void healthStep() {
        Pair<Integer, String> response = health();
        Assert.assertEquals(response.first(), 200);
        Health health = GSON.fromJson(response.second(), Health.class);
        Assert.assertEquals(health.getStatus(), "UP");
        Assert.assertEquals(health.getComponents().getDiskSpace().getStatus(), "UP");
        Assert.assertEquals(health.getComponents().getLivenessState().getStatus(), "UP");
        Assert.assertEquals(health.getComponents().getPing().getStatus(), "UP");
        Assert.assertEquals(health.getComponents().getReadinessState().getStatus(), "UP");
    }

    /**
     * шаг теста выполнить проверку liveness-probe
     */
    @Step("liveness-probe")
    public void livenessStep() {
        Pair<Integer, String> response = liveness();
        Assert.assertEquals(response.first(), 200);
        Assert.assertEquals(response.second(), "{\"status\":\"UP\"}");
    }

    /**
     * шаг теста выполнить проверку readiness-probe
     */
    @Step("readiness-probe")
    public void readinessStep() {
        Pair<Integer, String> response = readiness();
        Assert.assertEquals(response.first(), 200);
        Assert.assertEquals(response.second(), "{\"status\":\"UP\"}");
    }

    /**
     * Получить recordId любой АКТУАЛЬНОЙ записи из справочника, у которой атрибут равен значению
     *
     * @param catalog       Название каталога НСИ
     * @param attributeName Название атрибута
     * @param value         Значение атрибута
     * @return recordId
     */
    public String getRecordId(String catalog, String attributeName, String value) {
        String endPoint = "search_universal_widget/public/v1/catalogs/{catalogCode}/records/search";
        List<String> listValue = new ArrayList<>();
        List<Filter> listFilters = new ArrayList<>();
        Filter filter = new Filter();
        NSIAttributeName nsiAttributeName = new NSIAttributeName();
        AND and = new AND();
        listValue.add(value);

        filter.setKey(attributeName);
        filter.setValues(listValue);
        filter.setType("EXACT");

        listFilters.add(filter);

        and.setFilters(listFilters);

        nsiAttributeName.setAND(and);
        Map<String, String> pathParam = Map.of(
                "catalogCode", catalog
        );
        Map<String, String> queryParam = Map.of(
                "pageSize", "5",
                "pageNumber", "0");

        Response response = postJson(
                endPoint,
                pathParam,
                queryParam,
                null,
                nsiAttributeName,
                "получить recordId по атирибуту " + attributeName + " и значению " + value
        );
        assertThat(response.statusCode(), equalTo(200));
        NSIrecordId nsiRecordId = response.body().as(NSIrecordId.class);

        return getAny(nsiRecordId.getContent()).getRecordId();
    }

    /**
     * Получить recordId любой АКТУАЛЬНОЙ записи из справочника
     *
     * @param catalog Название каталога НСИ
     * @return recordId
     */
    public String getAnyRecordId(String catalog) {
        String endPoint = "search_universal_widget/public/v1/catalogs/{catalogCode}/records";
        Map<String, String> pathParam = Map.of(
                "catalogCode", catalog
        );
        Map<String, String> queryParam = Map.of(
                "includeArchived", "false",
                "phrase", "false",
                "division", "ADM",
                "page", "0",
                "size", "1000"
        );
        Response response = get(endPoint, pathParam, queryParam, null, "получить случайный recordId из " + catalog);
        assertThat(response.statusCode(), equalTo(200));
        NSIrecordId nsiRecordId = response.body().as(NSIrecordId.class);

        return getAny(nsiRecordId.getContent()).getRecordId();
    }

    /**
     * Получить значение атрибута по значению recordId
     *
     * @param recordId      ID запись в НСИ
     * @param attributeName Название атрибута
     * @return Значение атрибута
     */
    public String getAttribute(String recordId, String attributeName) {
        String endPoint = "search_universal_widget/public/v1/records/{recordId}";
        Map<String, String> pathParam = Map.of(
                "recordId", recordId
        );

        Response response = get(endPoint, pathParam, null, "Получить значение атрибута по значению recordId = " + recordId);
        assertThat(response.statusCode(), equalTo(200));
        String regex = "\"" + attributeName + "\":\"(.*?)\"";
        Matcher matcher = Pattern.compile(regex).matcher(response.body().asString());
        String ret = null;
        while (matcher.find()) {
            ret = matcher.group(1);
            if (ret != null) {
                break;
            }
        }
        Assert.assertNotNull(ret, "Атрибут " + attributeName + " не найден");
        return ret;
    }

    /**
     * Получить массив значений атрибута по значению recordId
     *
     * @param recordId      ID запись в НСИ
     * @param attributeName Название атрибута
     * @return Массив значений атрибута
     */
    public List<String> getAttributeList(String recordId, String attributeName) {
        String endPoint = "search_universal_widget/public/v1/records/{recordId}";
        Map<String, String> pathParam = Map.of(
                "recordId", recordId
        );
        Response response = get(endPoint, pathParam, null, "Получить значение атрибута по значению recordId = " + recordId);
        assertThat(response.statusCode(), equalTo(200));
        String regex = "\"attributeSet\":\\{(.*?)}";
        Matcher matcher = Pattern.compile(regex).matcher(response.body().asString());
        String ret = null;
        while (matcher.find()) {
            ret = matcher.group(1);
            if (ret != null) {
                break;
            }
        }

        Assert.assertNotNull(ret, "Атрибут " + attributeName + " не найден");
        String[] str = ret.replaceAll("\"", "").split(",");
        return Arrays.asList(str);

    }
}
