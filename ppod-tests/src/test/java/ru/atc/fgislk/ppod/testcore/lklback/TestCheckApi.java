package ru.atc.fgislk.ppod.testcore.lklback;

import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
import ru.atc.fgislk.ppod.testcore.lklback.enums.StatusEnum;
import ru.atc.fgislk.shared.testcomponents.enums.StendsDescriptionEnum;

import java.util.HashMap;
import java.util.Map;

import static ru.atc.fgislk.ppod.testcore.lklback.EndPoints.*;
import static ru.atc.fgislk.shared.testcomponents.enums.StendsDescriptionEnum.DEV;

/**
 * Проверка API <BR>
 * <a href="http://10.125.4.181:30001/swagger-ui/index.html#/">API ЛК Лесопользователя</a>
 */
@Slf4j
public class TestCheckApi {
    @Test(description = "GET Получение данных загруженных документов обращения", groups = {"sprint2"})
    void checkApiApplicationDocuments(@Optional("DEV") StendsDescriptionEnum stend) {
        log.info("GET Получение данных загруженных документов обращения");
        LklBack lklBack = new LklBack(stend);
        Map<String, String> pathParam = new HashMap<>(Map.of("applicationId", "4b4a1bb5-411f-495c-8014-c0e4765ee1cf"));
        lklBack.getUploadedApplicationDocuments(pathParam).then().assertThat().statusCode(200);
    }

    @Test(description = "GET Получение списка пакетов документов", groups = {"sprint2"})
    void checkApiApplications(@Optional("DEV") StendsDescriptionEnum stend) {
        LklBack lklBack = new LklBack(stend);
        lklBack.getListDocumentPackage().then().assertThat().statusCode(200);

        Map<String, String> queryParam = Map.of(
                "kind", "declaration", // extraction_glr
                "status", StatusEnum.DRAFT.toString(),
                "number", "0622-00010", //String.valueOf(rnd.nextInt(20) + 1),
                "createDate", "2022-06-30",
                "pageNumber", "0",
                "pageSize", "1"
        );
        lklBack.getListDocumentPackage(queryParam).then().assertThat().statusCode(200);
    }

    @Test(description = "GET Получение данных обращения", groups = {"sprint2"})
    void checkApiApplicationAppId(@Optional("DEV") StendsDescriptionEnum stend) {
        LklBack lklBack = new LklBack(stend);
        Map<String, String> pathParam = Map.of("applicationId", "6ea49c65-4788-48c1-8ff3-d3131f55bb46");
        lklBack.getCaseData(pathParam).then().assertThat().statusCode(200);
    }

    @Test(description = "GET Получение xml обращения в формате base64", groups = {"sprint2"})
    void checkApiApplicationXmlBase64(@Optional("DEV") StendsDescriptionEnum stend) {
        LklBack lklBack = new LklBack(stend);
        Map<String, String> pathParam = Map.of("applicationId", "6ea49c65-4788-48c1-8ff3-d3131f55bb46");
        lklBack.getXmlBase64(pathParam).then().assertThat().statusCode(200);
    }

    @Test(description = "GET Получение данных сформированных документов документов обращения", groups = {"sprint2"})
    void checkApiApplicationOutDocs(@Optional("DEV") StendsDescriptionEnum stend) {
        LklBack lklBack = new LklBack(stend);
        Map<String, String> pathParam = Map.of("applicationId", "6ea49c65-4788-48c1-8ff3-d3131f55bb46");
        lklBack.getGeneratedDocuments(pathParam).then().assertThat().statusCode(200);
    }

    @Test(description = "GET Получение списка лесохозяйственных объектов", groups = {"sprint2"})
    void checkApiApplicationObjects(@Optional("DEV") StendsDescriptionEnum stend) {
        LklBack lklBack = new LklBack(stend);
        Map<String, String> pathParam = Map.of("applicationId", "6ea49c65-4788-48c1-8ff3-d3131f55bb46");
        lklBack.getListForestryObjects(pathParam).then().assertThat().statusCode(200);
    }

    @Test(description = "GET Получение истории изменения статусов обращения", groups = {"sprint2"})
    void checkApiApplicationHistoty(@Optional("DEV") StendsDescriptionEnum stend) {
        LklBack lklBack = new LklBack(stend);
        Map<String, String> pathParam = Map.of("applicationId", "6ea49c65-4788-48c1-8ff3-d3131f55bb46");
        lklBack.getHistoryStatusChanges(pathParam).then().assertThat().statusCode(200);
    }

    @Test(description = "GET Получение списка документов схем", groups = {"sprint2"})
    void checkApiApplicationDocsScheme(@Optional("DEV") StendsDescriptionEnum stend) {
        LklBack lklBack = new LklBack(stend);
        Map<String, String> pathParam = Map.of("applicationId", "6ea49c65-4788-48c1-8ff3-d3131f55bb46");
        lklBack.getListSchematicDocuments(pathParam).then().assertThat().statusCode(200);
    }

    @Test(description = "GET Получение данных лесохозяйственного объекта", groups = {"sprint2"})
    void checkApiApplicationObject(@Optional("DEV") StendsDescriptionEnum stend) {
        LklBack lklBack = new LklBack(stend);
        Map<String, String> pathParam = Map.of("objectId", "69c28ff6-9406-4144-aeae-dcef20cb78f4");
        lklBack.getForestObjectData(pathParam).then().assertThat().statusCode(200);
    }

    @DataProvider(name = "pathRef")
    public Object[][] dataProviderPathRef() {
        return new Object[][]{
                {getListDocumentBatchStatuses, "schemas/lklback/DictionaryItemArray.json", "Получение списка статусов пакетов документов"},
                {getListForestUses, "schemas/lklback/DictionaryItemArray.json", "Получение списка видов использования леса"},
                {getListDocumentBatchTypes, "schemas/lklback/DictionaryItemArray.json", "Получение списка типов пакетов документов"},
                {getListFellingTypes, "schemas/lklback/DictionaryItemArray.json", "Получение списка видов рубки"},
                {getListFarmTypes, "schemas/lklback/DictionaryItemArray.json", "Получение списка типов хозяйств"},
                {getListDocumentTypes, "schemas/lklback/DictionaryItemArray.json", "Получение списка типов документов"},
                {getListDocumentKindsTypes, "schemas/lklback/DictionaryItemArray.json", "Получение списка видов документов"}
        };
    }

    @Test(description = "Проверка ref-controller", dataProvider = "pathRef", groups = {"sprint2"})
    void checkApiRef(String path, String schema, String allureName) {
        LklBack lklBack = new LklBack(DEV);
        lklBack.get(path, schema, allureName).then().assertThat().statusCode(200);
    }

}
