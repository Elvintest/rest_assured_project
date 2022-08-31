package ru.atc.fgislk.shared.testcomponents.filestorage;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.SpecificationQuerier;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.atc.fgislk.shared.testcomponents.back.BaseRestService;

import java.io.File;

import static io.restassured.RestAssured.given;

/**
 * простой клиент Файлового Хранилища, который умеет отправить 1 файл и получить по guid результат
 */
public class SimpleFileStorageService extends BaseRestService {

    private static Logger LOGGER = LoggerFactory.getLogger(SimpleFileStorageService.class);

    public SimpleFileStorageService(String uri) {
        super(uri);
    }

    /**
     * загрузить файл в файловое хранилище
     * @param file файл для загрузки в файловое хранилище
     * @return guid и md5 загруженного файла
     */
    @Step("Загрузить файл в файловое хранилище")
    public GuidMd5 userUpload(File file) {
        RequestSpecification reqSpecPost =given()
                .accept(ContentType.ANY)
                .header("Accept-Language", "ru-RU,ru;q=0.9,en-US;q=0.8,en;q=0.7")
                .header("Accept-Encoding", "gzip, deflate")
                .contentType("multipart/form-data; boundary=----" + RandomStringUtils.randomAlphanumeric(16))
                .baseUri(baseServiceApi)
                .multiPart("data", file);
        LOGGER.info("Отправить в ФХ {} файл {}", SpecificationQuerier.query(reqSpecPost).getURI(), file.getAbsolutePath());

        Allure.addAttachment("загрузить файл в файловое хранилище", SpecificationQuerier.query(reqSpecPost).getURI());
        Allure.addAttachment("путь к файлу", file.getAbsolutePath());

        Response response = reqSpecPost
                .when()
                .post("/userupload")
                .then()
                .statusCode(200)
                .extract().response();

        GuidMd5 res = new GuidMd5(response.getHeader("FileGuid"), response.getHeader("FileDigest"));
        LOGGER.info("результат загрузки {}, {}", res.getFileGuid(), res.getFileDigest());

        Allure.addAttachment("результат загрузки", res.toString());
        return res;
    }

    /**
     * получить файл из файлового хранилища по guid
     * @param guid
     * @return
     */
    public byte[] userDownload(String guid) {
        LOGGER.info("получить из ФХ файл по guid {}", guid);
        return getBytes("/userdownload/" + guid, "скачать файл из ФХ").second();
    }
}
