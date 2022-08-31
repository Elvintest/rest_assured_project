package ru.atc.fgislk.ppod.testcore.indocjournal;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.atc.fgislk.shared.testcomponents.back.BaseRestService;
import ru.atc.fgislk.shared.testcomponents.enums.StendsDescriptionEnum;

import java.util.Map;

public class InDocJournal extends BaseRestService {
    public InDocJournal(StendsDescriptionEnum stend) {
        super(stend.getPpodLk().getJuvd());
    }

    @Step("Получить ЖУВД по рег.номеру")
    public Response getJuvdByRegNumber(Map<String, String> pathParams) {
        return get(
                EndPoints.getJuvdByRegNumber,
                pathParams,
                "schemas/indocjournal/Journal.json",
                "Получить ЖУВД по рег.номеру");
    }

    @Step("Получить ЖУВД по рег.номеру")
    public Response getJuvdAll() {
        return get(
                EndPoints.getJuvdAll,
                "schemas/indocjournal/PageResponseJournal.json",
                "Получить ЖУВД по рег.номеру");
    }

    @Step("Получить запись ЖУВД по номеру")
    public Response getRecordJuvdByNumber(Map<String, String> pathParams) {
        return get(
                EndPoints.getRecordJuvdByNumber,
                pathParams,
                null,
                "schemas/indocjournal/Record.json",
                "Получить запись ЖУВД по номеру");
    }

    @Step("Получить записи журнала")
    public Response getRecordsByJournal(Map<String, String> queryParams) {
        return get(
                EndPoints.getRecordsByJournal,
                null,
                queryParams,
                "schemas/indocjournal/PageResponseRecord.json",
                "Получить записи журнала");
    }
}
