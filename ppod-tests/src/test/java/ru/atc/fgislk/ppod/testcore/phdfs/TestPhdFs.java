package ru.atc.fgislk.ppod.testcore.phdfs;

import com.codeborne.selenide.testng.SoftAsserts;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Listeners;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
import ru.atc.fgislk.ppod.testcore.phdfs.dto.*;
import ru.atc.fgislk.ppod.testcore.phdfs.dtodb.FileMeta;
import ru.atc.fgislk.ppod.testcore.phdfs.dtodb.FilePresence;
import ru.atc.fgislk.shared.testcomponents.enums.StendsDescriptionEnum;
import ru.atc.fgislk.shared.testcomponents.filestorage.GuidMd5;

import java.io.File;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static java.lang.Thread.sleep;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.StringContains.containsString;
import static ru.atc.fgislk.ppod.testcore.phdfs.EndPoints.*;

@Listeners({SoftAsserts.class})
@Slf4j
public class TestPhdFs {
    @Test(description = "Загрзка файла по прямой ссылке", groups = {"sprint3"})
    void testUploadFileLink(@Optional("DEV") StendsDescriptionEnum stend) throws SQLException, InterruptedException, IllegalAccessException, InstantiationException {
        log.info("Загрзка файла по прямой ссылке");
        log.info("Загрузить файл в файловое хранилище");
        PhdFsMetods phdFsMetods = new PhdFsMetods(stend);
        Map<String, String> pathParam = new HashMap<>(Map.of(
                "bucket", "file",
                "fileCode", "file",
                "fileType", "doctxt"
        ));

        Response response = phdFsMetods.postUploadFileStorage(pathParam);

        assertThat(response.statusCode(), equalTo(200));
        assertThat(response.header("FileDigest"), notNullValue());
        ConnectDB conn = new ConnectDB(stend);
        FilePresence status = conn.selectFile();
        assertThat(status.getStatus(), equalTo("uploaded"));
        String fileDigest = response.header("FileDigest");

        log.info("Скачать файл из файлового хранилища");
        response = phdFsMetods.getDownloadFileStorage(pathParam);
        assertThat(response.header("FileDigest"), equalTo(fileDigest));
    }

    @Test(description = "Загрузка файла и поучение файла по fileGUID", groups = {"sprint3"})
    void testUploadFileHUID(@Optional("DEV") StendsDescriptionEnum stend) {
        log.info("Загрузка файла и поучение файла по fileGUID");
        log.info("Загрузить файл в файловое хранилище и получить его GUID и Digest");
        PhdFsMetods phdFsMetods = new PhdFsMetods(stend);
        Response response = phdFsMetods.postUploanGuid();
        assertThat(response.statusCode(), equalTo(200));
        assertThat(response.header("FileGuid"), notNullValue());
        assertThat(response.header("FileDigest"), notNullValue());
        GuidMd5 guidMd5 = new GuidMd5()
                .fileGuid(response.header("FileGuid"))
                .fileDigest(response.header("FileDigest"));

        log.info("Скачать файл по GUID из файлового хранилища");
        Map<String, String> pathParam = new HashMap<>(Map.of(
                "guid", guidMd5.getFileGuid()
        ));
        response = phdFsMetods.getDownloadGuid(pathParam);
        assertThat(response.statusCode(), equalTo(200));
        assertThat(response.header("FileDigest"), equalTo(guidMd5.getFileDigest()));
    }

    @Test(description = "Получение ссылки на файл/метаданных/наличие файла после установки времени жизни файла", groups = {"sprint3"})
    void testGetLinkSetTimeout(@Optional("DEV") StendsDescriptionEnum stend) throws InterruptedException, SQLException, IllegalAccessException, InstantiationException {
        log.info("Получение ссылки на файл/метаданных/наличие файла после установки времени жизни файла");
        log.info("Загрузить файл в файловое хранилище и получить его GUID и Digest");
        PhdFsMetods phdFsMetods = new PhdFsMetods(stend);
        Response response = phdFsMetods.postUploanGuid();
        assertThat(response.statusCode(), equalTo(200));
        assertThat(response.header("FileGuid"), notNullValue());
        assertThat(response.header("FileDigest"), notNullValue());
        GuidMd5 guidMd5 = new GuidMd5()
                .fileGuid(response.header("FileGuid"))
                .fileDigest(response.header("FileDigest"));

        log.info("Получить FileLink по GUID");
        Map<String, String> queryParam = new HashMap<>(Map.of(
                "guid", guidMd5.getFileGuid()
        ));
        response = phdFsMetods.getFileLinkFromGuid(queryParam);
        assertThat(response.statusCode(), equalTo(200));
        assertThat(response.body().asString(), notNullValue());
        String fileLink = response.body().asString();

        log.info("Установить TTL файла");
        Map<String, String> pathParam = new HashMap<>(Map.of(
                "fileLink", fileLink
        ));
        queryParam = new HashMap<>(Map.of(
                "ttl", "PT1M" // Установим время жизни файла 1 минут
        ));
        phdFsMetods.getSetTTL(pathParam, queryParam).then().assertThat().statusCode(200);

        log.info("Получить метаданные файла");
        queryParam = new HashMap<>(Map.of(
                "withAttributes", "true"
        ));
        response = phdFsMetods.getEncoding(
                getMeta,
                pathParam,
                queryParam,
                "schemas/phffs/Meta.json",
                "Получить метаданные файла"
        );
        assertThat(response.statusCode(), equalTo(200));
        Meta meta = response.body().as(Meta.class);
        assertThat(meta.getMd5(), equalTo(guidMd5.getFileDigest()));
        assertThat(meta.getOriginalName(), equalTo("log.txt"));
        ConnectDB conn = new ConnectDB(stend);
        FileMeta status = conn.selectMeta(fileLink);
        assertThat(status.getFileType(), equalTo("UPLOADED_FILE"));
        assertThat(status.getOriginalFileName(), equalTo("log.txt"));
        assertThat(status.getMd5Sum(), equalTo(meta.getMd5()));

        log.info("Существует ли файл в файловом хранилище");
        phdFsMetods.getEncoding(
                getExist,
                pathParam,
                null,
                null,
                "Существует ли файл в файловом хранилище"
        ).then().assertThat().statusCode(200);

        log.info("Ожидание 1 минута");
        sleep(60000);

        log.info("Существует ли файл в файловом хранилище");
        phdFsMetods.getEncoding(
                getExist,
                pathParam,
                null,
                null,
                "Существует ли файл в файловом хранилище"
        ).then().assertThat().statusCode(404);
    }

    @Test(description = "Генерация временной ссылки / скачивание по этой ссылке", groups = {"sprint3"})
    void testGenerateTmpDownloadLink(@Optional("DEV") StendsDescriptionEnum stend) throws InterruptedException {
        log.info("Генерация временной ссылки / скачивание по этой ссылке");
        log.info("Добавить в хранилище ссылку на внешний ресурс");
        PhdFsMetods phdFsMetods = new PhdFsMetods(stend);
        Map<String, String> queryParam = new HashMap<>(Map.of(
                "uri", "http://www.eclipse.org/eclipse.org-common/themes/solstice/public/images/logo/eclipse-foundation-white-orange.svg"
        ));
        Response response = phdFsMetods.getEncoding(
                getPutExtLink,
                null,
                queryParam,
                null,
                "Добавить в хранилище ссылку на внешний ресурс"
        );
        assertThat(response.statusCode(), equalTo(201));
        String fileLink = response.body().asString();

        log.info("Получить временную ссылку для скачивания файла");
        Map<String, String> pathParam = new HashMap<>(Map.of(
                "fileLink", fileLink
        ));
        queryParam = new HashMap<>(Map.of(
                "ttl", "PT1M" // Установим время жизни файла 1 минут
        ));
        response = phdFsMetods.getEncoding(
                getTmpDownloadLink,
                pathParam,
                queryParam,
                null,
                "Получить временную ссылку для скачивания файла"
        );

        assertThat(response.statusCode(), equalTo(200));
        String token = response.body().asString().substring(response.body().asString().lastIndexOf("token=")).substring(6);

        log.info("Получить файл по токену");
        queryParam = new HashMap<>(Map.of(
                "token", token // Установим время жизни файла 1 минут
        ));
        response = phdFsMetods.getFileToken(queryParam);
        assertThat(response.statusCode(), equalTo(200));
        assertThat(response.body().asString(), containsString("Page-1"));

        log.info("Ожидание 1 минута");
        sleep(60000);

        log.info("Получить файл по токену");
        response = phdFsMetods.getFileToken(queryParam);
        assertThat(response.statusCode(), equalTo(410));
        assertThat(response.body().asString(), containsString("token expired"));

        log.info("Получить временную ссылку для скачивания файла");
        queryParam = new HashMap<>(Map.of(
                "token", token
        ));
        response = phdFsMetods.getEncoding(
                getResolveDownloadLink,
                null,
                queryParam,
                null,
                "Получить временную ссылку для скачивания файла"
        );
        assertThat(response.statusCode(), equalTo(200));
        assertThat(response.body().asString(), equalTo(fileLink));

    }

    @Test(description = "Загрзка файлов от внешних потребителей", groups = {"sprint3"})
    void testDownloadExternalConsumer(@Optional("DEV") StendsDescriptionEnum stend) {
        log.info("Загрзка файлов от внешних потребителей");
        log.info("Получить временную ссылку загрузки файла");
        PhdFsMetods phdFsMetods = new PhdFsMetods(stend);

        Map<String, String> queryParam = new HashMap<>(Map.of(
                "ttl", "P1D"
        ));
        Response response = phdFsMetods.getGenerateUploadLink(queryParam);
        assertThat(response.statusCode(), equalTo(200));
        assertThat(response.body().asString(), notNullValue());
        String token = response.body().asString();

        log.info("Загрузить файл по временной ссылке");
        Map<String, String> pathParam = new HashMap<>(Map.of(
                "token", token
        ));
        phdFsMetods.postUploadByToken(pathParam).then().assertThat().statusCode(200);

        log.info("Получить файл по токену");
        queryParam = new HashMap<>(Map.of(
                "token", token,
                "attachment", "true"
        ));
        response = phdFsMetods.getFileToken(queryParam);
        assertThat(response.statusCode(), equalTo(200));
        assertThat(response.header("Content-Disposition"), containsString("log.txt"));
    }

    @Test(description = "Архивация/разархивация", groups = {"sprint3"})
    void testZipUnZip(@Optional("DEV") StendsDescriptionEnum stend) throws InterruptedException {
        log.info("Архивация/разархивация");
        log.info("Формирует архив из заданных файлов и запускает процедуру архивации");
        PhdFsMetods phdFsMetods = new PhdFsMetods(stend);

        CompressRequest compressRequest =
                CompressRequest
                        .builder()
                        .zipFileName("zip")
                        .zipFileLink("file/file/zip2")
                        .filesToCompress(
                                FilesToCompress
                                        .builder()
                                        .additionalProp1("file/file/doctxt")
                                        .additionalProp2("file/file/doctxt")
//                                        .additionalProp3("file/file/doctxt")
                                        .build()

                        )
                        .build();
        Response response = phdFsMetods.postCompress(compressRequest);
        assertThat(response.statusCode(), equalTo(202));
        assertThat(response.header("Location"), notNullValue());
        String taskToken = response.header("Location").substring(10);

        log.info("Узнать статус операции архивации");
        Map<String, String> pathParam = new HashMap<>(Map.of(
                "taskToken", taskToken
        ));
        response = phdFsMetods.getEncoding(
                getCompress,
                pathParam,
                null,
                null,
                "Узнать статус операции архивации"
        );
        assertThat(response.statusCode(), oneOf(200, 303));
        assertThat(response.header("FileDigest"), notNullValue());
        GuidMd5 guidMd5 = new GuidMd5();
        guidMd5.fileDigest(response.header("FileDigest")).fileGuid(taskToken);

        log.info("Разархивирует указанный архив");
        pathParam = new HashMap<>(Map.of(
                "link", compressRequest.getZipFileLink()
        ));
        response = phdFsMetods.postJsonEncoding(
                postDecompressPath,
                pathParam,
                null,
                null,
                null,
                "Разархивирует указанный архив"
        );
        assertThat(response.statusCode(), equalTo(202));
        assertThat(response.header("Location"), notNullValue());
        taskToken = response.header("Location").substring(12);

        log.info("Ожидание 1 минута");
        sleep(60000);
        log.info("Узнать статус операции разархивирования");
        pathParam = new HashMap<>(Map.of(
                "taskToken", taskToken
        ));
        response = phdFsMetods.getEncoding(
                getDecompress,
                pathParam,
                null,
                null,
                "Узнать статус операции разархивирования"
        );
        assertThat(response.statusCode(), equalTo(200));
    }

    @Test(description = "putExtLinks, ", groups = {"sprint3"})
    void testOther(@Optional("DEV") StendsDescriptionEnum stend) {
        log.info("putExtLinks");
        log.info("Добавить в хранилище несколько ссылок на внешний ресурс");
        PhdFsMetods phdFsMetods = new PhdFsMetods(stend);

        PutExtLinksRequest putExtLinksRequest =
                PutExtLinksRequest
                        .builder()
                        .oneUriList("http://192.168.194.139:8080/static/6b99e9ad/images/48x48/red.png")
                        .oneUriList("http://192.168.194.139:8080/static/6b99e9ad/images/48x48/blue.png")
                        .fn(false)
                        .build();
        Response response = phdFsMetods.postPutExtLinks(putExtLinksRequest);
        assertThat(response.statusCode(), equalTo(201));
        List<String> listLinks = List.of(response.body().asString().substring(14, (response.body().asString().length() - 2)).split(","));

        log.info("Плучение файла по внутренней ссылке");

        for (int i = 0; i < listLinks.size(); i++) {
            String[] q = listLinks.get(i).split(",");
            String w = q[0].replaceAll("\"", "");
            String[] link = w.split(":");
            Map<String, String> pathParam = new HashMap<>(Map.of(
                    "internalLink", link[3]
            ));
            phdFsMetods.getEncoding(
                    getFileInternalLinks,
                    pathParam,
                    null,
                    null,
                    "Получение файла по внутренней ссылке"
            ).then().assertThat().statusCode(200);
        }

    }

}
