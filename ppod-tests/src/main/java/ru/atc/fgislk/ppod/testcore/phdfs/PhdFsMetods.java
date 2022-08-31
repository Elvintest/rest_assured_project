package ru.atc.fgislk.ppod.testcore.phdfs;

import io.qameta.allure.Step;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.QueryableRequestSpecification;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import io.restassured.specification.SpecificationQuerier;
import ru.atc.fgislk.ppod.testcore.phdfs.dto.CompressRequest;
import ru.atc.fgislk.ppod.testcore.phdfs.dto.PutExtLinksRequest;
import ru.atc.fgislk.shared.testcomponents.back.BaseRestService;
import ru.atc.fgislk.shared.testcomponents.enums.StendsDescriptionEnum;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static ru.atc.fgislk.ppod.testcore.phdfs.EndPoints.*;
import static ru.atc.fgislk.shared.testcomponents.Util.GSON;
import static ru.atc.fgislk.shared.testcomponents.Util.stringToAllureLog;

public class PhdFsMetods extends BaseRestService {
    public PhdFsMetods(StendsDescriptionEnum stend) {
        super(stend.getPpodLk().getFileStorage());
    }


    /**
     * get-запрос к сервису с логированием запроса
     *
     * @param path        путь запроса (endPoint)
     * @param pathParams  параметры пути
     * @param queryParams параметры запроса
     * @param pathSchema  путь к схеме json, для проверки ответа
     * @param allureName  название для сохранения запроса в лог Аллюр
     * @return полный ответ запроса
     */
    @Step("{allureName}")
    public Response getEncoding(String path, Map<String, String> pathParams, Map<String, String> queryParams, String pathSchema, String allureName) {
        RequestSpecification requestSpec = // new RequestSpecBuilder()
                given()
                        .log().all()
                        .baseUri(baseServiceApi)
                        .accept(ContentType.ANY)
                        .contentType(ContentType.JSON.withCharset("UTF-8"))
                        .urlEncodingEnabled(false);
        ResponseSpecification responseSpec = new ResponseSpecBuilder()
                .log(LogDetail.ALL)
                .build();

        if (pathParams != null)
            requestSpec.pathParams(pathParams);
        if (queryParams != null)
            requestSpec.queryParams(queryParams);
        if (pathSchema != null)
            responseSpec.body(matchesJsonSchemaInClasspath(pathSchema));

        Response response =
                requestSpec
//                .pathParams(pathParams)
//                .queryParams(queryParams)
                        .expect()
                        .when()
                        .get(path)
                        .then()
                        .spec(responseSpec)
                        .assertThat()
                        .extract()
                        .response();

        QueryableRequestSpecification queryRequest = SpecificationQuerier.query(requestSpec);
        stringToAllureLog(allureName, queryRequest.getURI());
        stringToAllureLog("результат", response.getBody().asString());

        return response;
    }

    /**
     * POST запрос
     *
     * @param path        путь
     * @param queryParams параметры запроса
     * @param pathSchema  схема для проверки
     * @param body        тело запроса
     * @param allureName  текст в allure
     * @return Response
     */
    public <T> Response postJsonEncoding(String path, Map<String, String> pathParams, Map<String, String> queryParams, String pathSchema, T body, String allureName) {

        RequestSpecification requestSpec = new RequestSpecBuilder()
                .log(LogDetail.ALL)
                .setBaseUri(baseServiceApi)
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.JSON.withCharset("UTF-8"))
                .setUrlEncodingEnabled(false)
                .build();
        ResponseSpecification responseSpec = new ResponseSpecBuilder()
                .log(LogDetail.ALL)
                .build();

        if (pathParams == null)
            pathParams = new HashMap<>();
        if (queryParams == null)
            queryParams = new HashMap<>();
        if (body != null)
            requestSpec.body(body);
        if (pathSchema != null)
            responseSpec.body(matchesJsonSchemaInClasspath(pathSchema));
        Response response = given()
                .spec(requestSpec)
                .pathParams(pathParams)
                .queryParams(queryParams)
                .expect()
                .when()
                .post(path)
                .then()
                .spec(responseSpec)
                .assertThat()
                .extract()
                .response();

        QueryableRequestSpecification queryRequest = SpecificationQuerier.query(requestSpec);
        stringToAllureLog(allureName, queryRequest.getURI());
        stringToAllureLog("тело", GSON.toJson(body));
        stringToAllureLog("результат", response.getBody().asString());
        return response;
    }

    @Step("Загрузить файл в файловое хранилище")
    public Response postUploadFileStorage(Map<String, String> pathParam) {
        return postMultipart(
                postUploadFileStorage,
                "data",
                "WebKitFormBoundary",
                pathParam,
                "Загрузить файл в файловое хранилище",
                new File(Objects.requireNonNull(getClass().getClassLoader().getResource("upload/log.txt")).getFile())
        );
    }

    @Step("Скачать файл из файлового хранилища")
    public Response getDownloadFileStorage(Map<String, String> pathParams) {
        return get(
                EndPoints.getDownloadFileStorage,
                pathParams,
                null,
                "Скачать файл из файлового хранилища"
        );
    }

    @Step("Загрузить файл в файловое хранилище и получить его GUID и Digest")
    public Response postUploanGuid() {
        return postMultipart(
                EndPoints.postUploanGuid,
                "data",
                "WebKitFormBoundary",
                "Загрузить файл в файловое хранилище и получить его GUID и Digest",
                new File(Objects.requireNonNull(getClass().getClassLoader().getResource("upload/log.txt")).getFile())
        );
    }

    @Step("Скачать файл по GUID из файлового хранилища")
    public Response getDownloadGuid(Map<String, String> pathParams) {
        return get(
                EndPoints.getDownloadGuid,
                pathParams,
                null,
                "Скачать файл по GUID из файлового хранилища"
        );
    }

    @Step("Получить FileLink по GUID")
    public Response getFileLinkFromGuid(Map<String, String> queryParams) {
        return get(
                getFileLinkFromGuid,
                null,
                queryParams,
                null,
                "Получить FileLink по GUID"
        );
    }

    @Step("Установить TTL файла")
    public Response getSetTTL(Map<String, String> pathParams, Map<String, String> queryParams) {
        return getEncoding(
                getSetTTL,
                pathParams,
                queryParams,
                null,
                "Установить TTL файла"
        );
    }

    @Step("Получить файл по токену")
    public Response getFileToken(Map<String, String> queryParams) {
        return get(
                getFileToken,
                null,
                queryParams,
                null,
                "Получить файл по токену"
        );

    }

    @Step("Получить временную ссылку загрузки файла")
    public Response getGenerateUploadLink(Map<String, String> queryParams) {
        return get(
                getGenerateUploadLink,
                null,
                queryParams,
                null,
                "Получить временную ссылку загрузки файла"
        );
    }

    @Step("Загрузить файл по временной ссылке")
    public Response postUploadByToken(Map<String, String> pathParams) {
        return postMultipart(
                postUploadByToken,
                "data",
                "WebKitFormBoundary",
                pathParams,
                "Загрузить файл по временной ссылке",
                new File(Objects.requireNonNull(getClass().getClassLoader().getResource("upload/log.txt")).getFile())
        );
    }

    @Step("Формирует архив из заданных файлов и запускает процедуру архивации")
    public Response postCompress(CompressRequest compressRequest) {
        return postJson(
                postCompress,
                compressRequest,
                "Формирует архив из заданных файлов и запускает процедуру архивации"
        );
    }

    @Step("Добавить в хранилище несколько ссылок на внешний ресурс")
    public Response postPutExtLinks(PutExtLinksRequest putExtLinksRequest) {
        return postJson(
                postPutExtLinks,
//                "schemas/phffs/PutExtLinksResponse.json",
                putExtLinksRequest,
                "Добавить в хранилище несколько ссылок на внешний ресурс"
        );
    }

}
