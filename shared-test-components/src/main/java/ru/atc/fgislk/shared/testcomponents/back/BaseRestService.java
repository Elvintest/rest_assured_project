package ru.atc.fgislk.shared.testcomponents.back;

import io.restassured.builder.MultiPartSpecBuilder;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.QueryableRequestSpecification;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import io.restassured.specification.SpecificationQuerier;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.internal.collections.Pair;
import ru.atc.fgislk.shared.testcomponents.Util;
import ru.atc.fgislk.shared.testcomponents.filestorage.SimpleFileStorageService;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.config.MultiPartConfig.multiPartConfig;
import static ru.atc.fgislk.shared.testcomponents.Util.GSON;
import static ru.atc.fgislk.shared.testcomponents.Util.stringToAllureLog;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

/**
 * Базовый класс, предоставляет сервис выполнения REST-запросов. Конкретные запросы определяются в потомках
 */
public class BaseRestService {

    private final static Logger LOGGER =  LoggerFactory.getLogger(BaseRestService .class);
    /**
     * Адрес сервиса
     */
    protected final String baseServiceApi;

    private final RequestSpecification reqSpecPost;
    //private RequestSpecification reqSpecGet;
    //private final RequestSpecification reqSpecGetByte;
    private final RequestSpecification reqSpecPut;

    public BaseRestService(String apiUrl) {
        baseServiceApi = apiUrl;
        reqSpecPost = given()
                .accept(ContentType.ANY)
                .contentType(ContentType.JSON.withCharset("UTF-8"))
                .baseUri(baseServiceApi);
//        reqSpecGetByte = given()
//                .accept(ContentType.ANY)
//                .contentType(ContentType.JSON.withCharset("UTF-8"))
//                .baseUri(baseServiceApi);
        reqSpecPut = given()
                .accept(ContentType.ANY)
                .contentType(ContentType.JSON.withCharset("UTF-8"))
                .baseUri(baseServiceApi);
    }

//    /**
//     * Фильтер allure
//     */
//    private final AllureRestAssured filterAllure = new AllureRestAssured();

    /**
     * get-запрос к сервису с логированием запроса
     *
     * @param path       подготовленный get-запрос
     * @param allureName название для сохранения запроса в лог Аллюр
     * @return пара(Код ответа, Текст ответа)
     */
    protected Pair<Integer, String> get(String path, String allureName) {
        return get(path, new HashMap<>(), allureName);
    }

    /**
     * get-запрос к сервису с логированием запроса
     *
     * @param path        подготовленный get-запрос
     * @param queryParams параметры запроса
     * @param allureName  название для сохранения запроса в лог Аллюр
     * @return пара(Код ответа, Текст ответа)
     */
    protected Pair<Integer, String> get(String path, Map<String, String> queryParams, String allureName) {
        RequestSpecification reqSpecGet = given()
                .baseUri(baseServiceApi)
                .accept(ContentType.ANY)
                .queryParams(queryParams);
        Response response = reqSpecGet
                .when()
                .get(path)
                .then()
                .extract().response();
        QueryableRequestSpecification queryRequest = SpecificationQuerier.query(reqSpecGet);
        stringToAllureLog(allureName, queryRequest.getURI());

        Pair<Integer, String> pair = new Pair<>(response.getStatusCode(), response.getBody().asString());
        stringToAllureLog("результат", pair.second());
        return pair;
    }

    /**
     * get-запрос к сервису с логированием запроса
     *
     * @param path       путь запроса (endPoint)
     * @param pathSchema путь к схеме json, для проверки ответа
     * @param allureName название для сохранения запроса в лог Аллюр
     * @return полный ответ запроса
     */
    public Response get(String path, String pathSchema, String allureName) {
        return get(path, new HashMap<>(), new HashMap<>(), pathSchema, allureName);
    }

    /**
     * get-запрос к сервису с логированием запроса
     *
     * @param path       путь запроса (endPoint)
     * @param pathParams параметры пути
     * @param pathSchema путь к схеме json, для проверки ответа
     * @param allureName название для сохранения запроса в лог Аллюр
     * @return полный ответ запроса
     */
    public Response get(String path, Map<String, String> pathParams, String pathSchema, String allureName) {
        return get(path, pathParams, new HashMap<>(), pathSchema, allureName);
    }

// TODO Переделать все запросы на возврат Response
//    /**
//     * get-запрос к сервису с логированием запроса
//     * @param path путь запроса (endPoint)
//     * @param pathParams параметры пути
//     * @param allureName название для сохранения запроса в лог Аллюр
//     * @return полный ответ запроса
//     */
//    public Response get(String path, Map<String, String> pathParams, String allureName) {
//        return get(path, pathParams, new HashMap<>(), null, allureName);
//    }

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
    public Response get(String path, Map<String, String> pathParams, Map<String, String> queryParams, String pathSchema, String allureName) {
        RequestSpecification requestSpec = given()
//                new RequestSpecBuilder()
                .log().all()
                .baseUri(baseServiceApi)
                .accept(ContentType.ANY)
                .contentType(ContentType.JSON.withCharset("UTF-8"));
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
     * get-запрос к сервису. Логирует количество принятый байт
     *
     * @param path       подготовленный get-запрос
     * @param allureName название для сохранения запроса в лог Аллюр
     * @return пара(Код ответа, принятые байты)
     */
    protected Pair<Integer, byte[]> getBytes(String path, String allureName) {
        return getBytes(path, new HashMap<>(), allureName);
    }

    /**
     * get-запрос к сервису. Логирует количество принятый байт
     *
     * @param path        подготовленный get-запрос
     * @param queryParams параметры запроса
     * @param allureName  название для сохранения запроса в лог Аллюр
     * @return пара(Код ответа, принятые байты)
     */
    protected Pair<Integer, byte[]> getBytes(String path, Map<String, String> queryParams, String allureName) {
        RequestSpecification reqSpecGetByte = given()
                .accept(ContentType.ANY)
                .baseUri(baseServiceApi)
                .basePath(path)
                .queryParams(queryParams);

        QueryableRequestSpecification queryRequest = SpecificationQuerier.query(reqSpecGetByte);
        LOGGER.info("GET байты {}", queryRequest.getURI());
        stringToAllureLog(allureName, queryRequest.getURI());

        Response response = reqSpecGetByte
//                .filter(filterAllure.setRequestAttachmentName(allureName).setResponseAttachmentName("Результат"))
                .when()
                .get()
                .then()
                .extract().response();

        Pair<Integer, byte[]> pair = new Pair<>(response.getStatusCode(), response.getBody().asByteArray());

        // залогируем текстовую ошибку, если ответ не тот
        if (pair.first() != 200) {
            stringToAllureLog("ответ сервиса", new String(pair.second(), StandardCharsets.UTF_8));
            return pair;
        }

        stringToAllureLog("длина ответа в байтах", response.getHeader("Content-Length"));
        return pair;
    }

    /**
     * get-запрос к сервису, без логирования запроса
     *
     * @param path подготовленный get-запрос
     * @return пара(Код ответа, Текст ответа)
     */
    protected Pair<Integer, String> getNoLog(String path) {
        return getNoLog(path, new HashMap<>());
    }

    /**
     * get-запрос к сервису, без логирования запроса
     *
     * @param path        подготовленный get-запрос
     * @param queryParams параметры запроса
     * @return пара(Код ответа, Текст ответа)
     */
    protected Pair<Integer, String> getNoLog(String path, Map<String, String> queryParams) {
        RequestSpecification reqSpecGet = given()
                .accept(ContentType.ANY)
                .baseUri(baseServiceApi)
                .queryParams(queryParams);
        Response response = reqSpecGet
                .queryParams(queryParams)
                .when()
                .get(path)
                .then()
                .extract().response();
        return new Pair<>(response.getStatusCode(), response.getBody().asString());
    }

    /**
     * post-запрос к сервису, в качестве тела запроса json
     *
     * @param path       адрес
     * @param json       тело запроса в формате json
     * @param allureName текст для сохранения запроса в лог Аллюр
     * @return пара(Код ответа, Текст ответа)
     */
    protected Pair<Integer, String> postJson(String path, String json, String allureName) {
        return postJson(path, json, new HashMap<>(), allureName);
    }

    /**
     * post-запрос к сервису, в качестве тела запроса json
     *
     * @param path        адрес
     * @param json        тело запроса в формате json
     * @param queryParams параметры запроса
     * @param allureName  текст для сохранения запроса в лог Аллюр. Если название лога пустое или null, то запрос не логгируется
     * @return пара(Код ответа, Текст ответа)
     */
    protected Pair<Integer, String> postJson(String path, String json, Map<String, String> queryParams, String allureName) {

        Response response = reqSpecPost
                .header("Accept-Encoding", "gzip, deflate, br")
                .header("Content-Lenght", json.length())
                .header("Connection", "keep-alive")
//                .filter(filterAllure.setRequestAttachmentName(allureName).setResponseAttachmentName("Результат"))
                .queryParams(queryParams)
                .body(json)
                .when()
                .post(path)
                .then()
                .contentType(ContentType.JSON.withCharset("UTF-8"))
                .extract().response();

        QueryableRequestSpecification queryRequest = SpecificationQuerier.query(reqSpecPost);
        stringToAllureLog(allureName, queryRequest.getURI());
        stringToAllureLog("тело", json);
        stringToAllureLog("результат", response.getBody().asString());

        return new Pair<>(response.getStatusCode(), response.getBody().asString());
    }

    /**
     * POST запрос
     *
     * @param path       путь
     * @param body       тело запроса
     * @param allureName текст в allure
     * @return Response
     */
    public <T> Response postJson(String path, T body, String allureName) {
        return postJson(path, null, body, allureName);
    }

    /**
     * POST запрос
     *
     * @param path       путь
     * @param schema     схема для проверки
     * @param body       тело запроса
     * @param allureName текст в allure
     * @return Response
     */
    public <T> Response postJson(String path, String schema, T body, String allureName) {
        return postJson(path, new HashMap<>(), new HashMap<>(), schema, body, allureName);
    }
// TODO Переделать все запросы на возврат Response
//    /**
//     * POST запрос
//     *
//     * @param path       путь
//     * @param schema     схема для проверки
//     * @param allureName текст в allure
//     * @return Response
//     */
//    public <T> Response postJson(String path, String schema, String allureName) {
//        return postJson(path, new HashMap<>(), new HashMap<>(), schema, null, allureName);
//    }
    /**
     * POST запрос
     *
     * @param path       путь
     * @param pathParams параметры пути
     * @param schema     схема для проверки
     * @param body       тело запроса
     * @param allureName текст в allure
     * @return Response
     */
    public <T> Response postJson(String path, Map<String, String> pathParams, String schema, T body, String allureName) {
        return postJson(path, pathParams, new HashMap<>(), schema, body, allureName);
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
    public <T> Response postJson(String path, Map<String, String> pathParams, Map<String, String> queryParams, String pathSchema, T body, String allureName) {

        RequestSpecification requestSpec = new RequestSpecBuilder()
                .log(LogDetail.ALL)
                .setBaseUri(baseServiceApi)
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.JSON.withCharset("UTF-8"))
                .build();
        ResponseSpecification responseSpec = new ResponseSpecBuilder()
                .log(LogDetail.ALL)
                .build();

        if (pathParams == null)
            pathParams = new HashMap<>();
        if (queryParams == null)
            queryParams = new HashMap<>();
        if (body != null)
            requestSpec.body(Util.GSON.toJson(body));
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

        QueryableRequestSpecification queryRequest = SpecificationQuerier.query(reqSpecPost);
        stringToAllureLog(allureName, queryRequest.getURI());
        stringToAllureLog("тело", GSON.toJson(body));
        stringToAllureLog("результат", response.getBody().asString());
        return response;
    }

    /**
     * Выполнить post-запрос без тела
     *
     * @param path       адрес
     * @param allureName текст для сохранения запроса в лог Аллюр
     * @return пара(Код ответа, Текст ответа)
     */
    protected Pair<Integer, String> post(String path, String allureName) {
        return post(path, new HashMap<>(), allureName);
    }

    /**
     * Выполнить post-запрос без тела
     *
     * @param path        адрес
     * @param queryParams параметры запроса
     * @param allureName  текст для сохранения запроса в лог Аллюр
     * @return пара(Код ответа, Текст ответа)
     */
    protected Pair<Integer, String> post(String path, Map<String, String> queryParams, String allureName) {
        Response response = reqSpecPost
                .header("Accept-Encoding", "gzip, deflate, br")
                .header("Connection", "keep-alive")
//                .filter(filterAllure.setRequestAttachmentName(allureName).setResponseAttachmentName("Результат"))
                .queryParams(queryParams)
                .when()
                .post(path)
                .then()
                .extract().response();
        QueryableRequestSpecification queryRequest = SpecificationQuerier.query(reqSpecPost);
        stringToAllureLog(allureName, queryRequest.getURI());

        Pair<Integer, String> pair = new Pair<>(response.getStatusCode(), response.getBody().asString());
        stringToAllureLog("результат", pair.second());
        return pair;
    }

    protected Pair<Integer, String> postMultipart(String path, Map<String, String> queryParams, String allureName, File... files) {
        RequestSpecification reqSpecPost = given()
                .accept(ContentType.ANY)
                .header("Accept-Language", "ru-RU,ru;q=0.9,en-US;q=0.8,en;q=0.7")
                .header("Accept-Encoding", "gzip, deflate")
                .contentType("multipart/form-data; boundary=----" + RandomStringUtils.randomAlphanumeric(16))
                .baseUri(baseServiceApi);

        for (File file : files) {
            reqSpecPost.multiPart("files", file);
        }

        Response response = reqSpecPost
                .queryParams(queryParams)
                .when()
                .post(path)
                .then()
                .extract().response();
        QueryableRequestSpecification queryRequest = SpecificationQuerier.query(reqSpecPost);
        stringToAllureLog(allureName, queryRequest.getURI());

        return new Pair<>(response.getStatusCode(), response.getBody().asString());
    }

    /**
     * Выполнить post запрос с вложением multipart
     *
     * @param json         тело запроса в формате json
     * @param sendFile     отправляемый файл
     * @param formBoundary дополнительная строка определяющая начало вложения. (например WebKitFormBoundary)
     * @param controlName  название контрола вложения
     * @param fileName     название отправляемого файла
     * @return пара(Код ответа, Текст ответа)
     */
    protected Pair<Integer, String> postMultipart(String json, File sendFile, String formBoundary, String controlName, String fileName) {
        String rndString = formBoundary + RandomStringUtils.randomAlphanumeric(16);
        Response response = given()
                .header("Accept", "application/json, text/plain, */*")
                .header("Accept-Language", "ru-RU,ru;q=0.9,en-US;q=0.8,en;q=0.7")
                .header("Accept-Encoding", "gzip, deflate")
                .contentType("multipart/form-data; boundary=----" + rndString)
                .config(
                        RestAssuredConfig.config()
                                .httpClient(HttpClientConfig.httpClientConfig()
                                        .httpMultipartMode(HttpMultipartMode.BROWSER_COMPATIBLE))
                                .multiPartConfig(
                                        multiPartConfig()
                                                .defaultBoundary("------" + rndString)
                                )
                )
                .and()
                .body(json)
                .multiPart(
                        new MultiPartSpecBuilder(json)
                                .controlName(controlName)
                                .fileName(fileName)
                                .mimeType("application/json")
                                .charset(StandardCharsets.UTF_8)
                                .build()
                )
                .multiPart("scanFile", sendFile, "image/png")
                .when()
                .post("/save")
                .then()
                .extract()
                .response();
        return new Pair<>(response.getStatusCode(), response.getBody().asString());
    }

    /**
     * post-запрос к сервису. Логирует количество принятый байт
     *
     * @param path       подготовленный post-запрос (без параметров)
     * @param body       тело запроса
     * @param allureName текст для сохранения запроса в лог Аллюр
     * @return пара(Код ответа, принятые байты)
     */
    protected Pair<Integer, byte[]> postBytes(String path, String body, String allureName) {
        return postBytes(path, body, allureName, new HashMap<>());
    }

    /**
     * POST multipart запрос
     *
     * @param path         путь
     * @param controlName  название поля файла
     * @param formBoundary дополнительная строка определяющая начало вложения. (например WebKitFormBoundary)
     * @param allureName   название в allere
     * @param files        файлы для загрузки
     * @return Response
     */
    public Response postMultipart(String path, String controlName, String formBoundary, String allureName, File... files) {
        return postMultipart(path, controlName, formBoundary, new HashMap<>(), new HashMap<>(), allureName, files);
    }

    /**
     * POST multipart запрос
     *
     * @param path         путь
     * @param controlName  название поля файла
     * @param formBoundary дополнительная строка определяющая начало вложения. (например WebKitFormBoundary)
     * @param pathParams   параметры строки
     * @param allureName   название в allure
     * @param files        файлы для загрузки
     * @return Response
     */
    public Response postMultipart(String path, String controlName, String formBoundary, Map<String, String> pathParams, String allureName, File... files) {
        return postMultipart(path, controlName, formBoundary, pathParams, new HashMap<>(), allureName, files);
    }

    /**
     * POST multipart запрос
     *
     * @param path         путь
     * @param controlName  название поля файла
     * @param formBoundary дополнительная строка определяющая начало вложения. (например WebKitFormBoundary)
     * @param pathParams   параметры строки
     * @param queryParams  параметры запроса
     * @param allureName   название в allure
     * @param files        файлы для загрузки
     * @return Response
     */
    public Response postMultipart(String path, String controlName, String formBoundary, Map<String, String> pathParams, Map<String, String> queryParams, String allureName, File... files) {
        String rndString = formBoundary + RandomStringUtils.randomAlphanumeric(16);
        RequestSpecification reqSpecPost = given()
                .log().all()
                .accept(ContentType.ANY)
                .header("Accept-Language", "ru-RU,ru;q=0.9,en-US;q=0.8,en;q=0.7")
                .header("Accept-Encoding", "gzip, deflate")
                .contentType("multipart/form-data; boundary=----" + rndString)
                .baseUri(baseServiceApi);

        for (File file : files) {
            reqSpecPost.multiPart(controlName, file);
        }

        if (pathParams != null)
            reqSpecPost.pathParams(pathParams);

        if (queryParams != null)
            reqSpecPost.queryParams(queryParams);

        Response response = reqSpecPost
                .when()
                .post(path)
                .then()
                .log().all()
                .extract().response();
        QueryableRequestSpecification queryRequest = SpecificationQuerier.query(reqSpecPost);
        stringToAllureLog(allureName, queryRequest.getURI());

        return response;
    }

    /**
     * post-запрос к сервису. Логирует количество принятый байт
     *
     * @param path       подготовленный post-запрос (без параметров)
     * @param body       тело запроса
     * @param allureName текст для сохранения запроса в лог Аллюр
     * @param params     параметры запроса
     * @return пара(Код ответа, принятые байты)
     */
    protected Pair<Integer, byte[]> postBytes(String path, String body, String allureName, Map<String, String> params) {

        Response response = reqSpecPost
                .queryParams(params)
//                .filter(filterAllure.setRequestAttachmentName(allureName).setResponseAttachmentName("Результат"))
                .body(Util.GSON.toJson(body))
                .when()
                .post(path)
                .then()
                .extract()
                .response();
        QueryableRequestSpecification queryRequest = SpecificationQuerier.query(reqSpecPost);

        stringToAllureLog(allureName, queryRequest.getURI());
        stringToAllureLog("тело запроса", queryRequest.getBody());

        Pair<Integer, byte[]> pair = new Pair<>(response.getStatusCode(), response.getBody().asByteArray());

        // залогируем текстовую ошибку, если ответ не тот
        if (pair.first() != 200) {
            stringToAllureLog("ответ сервиса", new String(pair.second(), StandardCharsets.UTF_8));
            return pair;
        }

        stringToAllureLog("длина ответа в байтах", response.getHeader("Content-Length"));

        return pair;
    }

    /**
     * put-запрос к сервису с логированием запроса
     *
     * @param path       подготовленный get-запрос
     * @param allureName название для сохранения запроса в лог Аллюр
     * @return пара(Код ответа, Текст ответа)
     */
    protected Pair<Integer, String> put(String path, String allureName) {
        return put(path, new HashMap<>(), allureName);
    }

    /**
     * put-запрос к сервису с логированием запроса
     *
     * @param path        подготовленный get-запрос
     * @param queryParams параметры запроса
     * @param allureName  название для сохранения запроса в лог Аллюр
     * @return пара(Код ответа, Текст ответа)
     */
    protected Pair<Integer, String> put(String path, Map<String, String> queryParams, String allureName) {
        Response response = reqSpecPut
//                .filter(filterAllure.setRequestAttachmentName(allureName).setResponseAttachmentName("Результат"))
                .queryParams(queryParams)
                .when()
                .put(path)
                .then()
                .extract().response();
        QueryableRequestSpecification queryRequest = SpecificationQuerier.query(reqSpecPost);
        stringToAllureLog(allureName, queryRequest.getURI());

        Pair<Integer, String> pair = new Pair<>(response.getStatusCode(), response.getBody().asString());
        stringToAllureLog("результат", pair.second());
        return pair;
    }


    /**
     * PUT запрос
     *
     * @param path       путь
     * @param body       тело запроса
     * @param allureName название в allere
     * @return Response
     */
    public <T> Response put(String path, T body, String allureName) {
        return put(path, new HashMap<>(), body, allureName);
    }

    /**
     * PUT запрос
     *
     * @param path       путь
     * @param pathParams параметры пути
     * @param body       тело запроса
     * @param allureName название в allere
     * @return Response
     */
    public <T> Response put(String path, Map<String, String> pathParams, T body, String allureName) {
        return put(path, pathParams, new HashMap<>(), body, allureName);
    }

    /**
     * PUT запрос
     *
     * @param path        путь
     * @param pathParams  параметры пути
     * @param queryParams параметры запроса
     * @param body        тело запроса
     * @param allureName  название в allere
     * @return Response
     */
    public <T> Response put(String path, Map<String, String> pathParams, Map<String, String> queryParams, T body, String allureName) {
        return put(path, pathParams, queryParams, null, body, allureName);
    }

    /**
     * PUT запрос
     *
     * @param path        путь
     * @param pathParams  параметры пути
     * @param queryParams параметры запроса
     * @param schema      схема ответа
     * @param body        тело запроса
     * @param allureName  название в allere
     * @return Response
     */
    public <T> Response put(String path, Map<String, String> pathParams, Map<String, String> queryParams, String schema, T body, String allureName) {
        RequestSpecification requestSpec = new RequestSpecBuilder()
                .log(LogDetail.ALL)
                .setBaseUri(baseServiceApi)
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.JSON.withCharset("UTF-8"))
                .build();
        ResponseSpecification responseSpec = new ResponseSpecBuilder()
                .log(LogDetail.ALL)
                .build();

        if (schema != null)
            responseSpec.body(matchesJsonSchemaInClasspath(schema));

        Response response = given()
                .spec(requestSpec)
                .body(Util.GSON.toJson(body))
                .expect()
                .when()
                .put(path)
                .then()
                .spec(responseSpec)
                .assertThat()
                .extract()
                .response();
        QueryableRequestSpecification queryRequest = SpecificationQuerier.query(reqSpecPost);
        stringToAllureLog(allureName, queryRequest.getURI());
        stringToAllureLog("результат", response.getBody().asString());
        return response;
    }


}
