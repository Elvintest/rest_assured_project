package ru.atc.fgislk.ppod.testcore.lklfront.api;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
import ru.atc.fgislk.ppod.testcore.lklback.EndPoints;
import ru.atc.fgislk.ppod.testcore.lklback.dto.*;
import ru.atc.fgislk.ppod.testcore.lklback.enums.TypeEnum;
import ru.atc.fgislk.ppod.testcore.lklfront.services.LklFront;
import ru.atc.fgislk.shared.testcomponents.Util;
import ru.atc.fgislk.shared.testcomponents.enums.StendsDescriptionEnum;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static ru.atc.fgislk.ppod.testcore.lklback.EndPoints.*;
import static ru.atc.fgislk.ppod.testcore.lklback.dto.Period.*;
import static ru.atc.fgislk.ppod.testcore.lklback.enums.ApplicationKindEnum.DECLARATION;
import static ru.atc.fgislk.ppod.testcore.lklback.enums.KindEnum.UNKNOWN;
import static ru.atc.fgislk.ppod.testcore.lklback.enums.TypeSubjectEnum.PARTNER;
import static ru.atc.fgislk.ppod.testcore.lklback.enums.UsageTypeEnum.*;

public class TestFrontLk {

    @Test(description = "Создание Лесной декларации", groups = {"sprint2"})
    void createForestDeclaration(@Optional("DEV") StendsDescriptionEnum stend) {
        LklFront lklFront = new LklFront(stend);

        // Сохранение черновика обращения
        ApplicationSaveRequest applicationSaveRequest =
                ApplicationSaveRequest
                        .builder()
                        .applicationKind(DECLARATION.getName())
                        .declarationInfo(DeclarationInfo
                                .builder()
                                .number("35419")
                                .contract(Contract
                                        .builder()
                                        .date("2022-06-27")
                                        .number("Документ аренды")
                                        .type("2")
                                        .build()
                                )
                                .executiveAuthority(ExecutiveAuthority
                                        .builder()
                                        .code("1")
                                        .title("Орган 1")
                                        .build()
                                )
                                .forestry(
                                        Forestry
                                                .builder()
                                                .code("1")
                                                .title("Лесничество 1")
                                                .build()
                                )
                                .period(
                                        builder()
                                                .from("2022-01-01")
                                                .to("2022-12-31")
                                                .build()
                                )
                                .usageType(
                                        UsageType
                                                .builder()
                                                .code(HARVESTINGOBJECT.getName())
                                                .title(HARVESTINGOBJECT.getTitle())
                                                .build()
                                )
                                .usageType(
                                        UsageType
                                                .builder()
                                                .code(OTHERUSAGEOBJECTS.getName())
                                                .title(OTHERUSAGEOBJECTS.getTitle())
                                                .build()
                                )
                                .build()
                        )
                        .region(
                                Region
                                        .builder()
                                        .code("2")
                                        .title("Субъект 2")
                                        .build()
                        )
                        .subject(
                                Subject
                                        .builder()
                                        .type(PARTNER.getName())
                                        .employeeInfo(
                                                EmployeeInfo
                                                        .builder()
                                                        .basisAuthorityGenitive("Доверенности")
                                                        .fioGenitive("Зеленскому Александру Викторовичу")
                                                        .postGenitive("Представителя")
                                                        .build()
                                        )
                                        .person(
                                                Person
                                                        .builder()
                                                        .firstname("Иван")
                                                        .lastname("Иванов")
                                                        .patronymic("Иванович")
                                                        .identityDocument(
                                                                IdentityDocument
                                                                        .builder()
                                                                        .number("123789")
                                                                        .series("6020")
                                                                        .type(
                                                                                Type
                                                                                        .builder()
                                                                                        .code("2")
                                                                                        .title("Документ 2")
                                                                                        .build()
                                                                        )
                                                                        .build()
                                                        )
                                                        .build()
                                        )
                                        .build()
                        )
                        .build();


        // Выполним запрос и получим id
        String applicationId = lklFront.postJson(
                "/api" + EndPoints.postSavingDraftCase,
                "schemas/ApplicationSaveRequest.json",
                applicationSaveRequest,
                "Сохранение черновика обращения").body().as(ApplicationSaveResponse.class).getId();

        Map<String, String> pathParam = Map.of("applicationId", applicationId);
        Map<String, String> queryParam = Map.of(
                "pageSize", "5",
                "pageNumber", "0");
        Response response = lklFront.get(
                "/api" + EndPoints.getListForestryObjects,
                pathParam,
                queryParam,
                "schemas/ForestObjectShortWithCount.json",
                "Получение списка лесохозяйственных объектов"
        );

        response = lklFront.get(
                "/api" + EndPoints.getCaseData,
                pathParam,
                "schemas/ApplicationInfo.json",
                "Получение данных обращения"
        );

        // Добавить объект
        response = lklFront.get(
                "/api" + getListForestUses,
                "schemas/DictionaryItemArray.json",
                "Получение списка видов использования леса"
        );

        response = lklFront.get(
                "/api" + getListSchematicDocuments,
                pathParam,
                "schemas/DocumentInfoArray.json",
                "Получение списка документов схем"
        );
        response = lklFront.get(
                "/api" + getListFellingTypes,
                "schemas/DictionaryItemArray.json",
                "Получение списка видов рубки"
        );


        // Загрузка файла
        String pathFile = lklFront.postMultipart(
                "/api" + postUploadFile,
                "file",
                "WebKitFormBoundary",
                "Загрузка файла",
                new File(Objects.requireNonNull(getClass().getClassLoader().getResource("upload/Image_002.png")).getFile())
        ).body().asString();
        // Загрузка подписи
        String pathFileSig = lklFront.postMultipart(
                "/api" + postUploadFile,
                "file",
                "WebKitFormBoundary",
                "файла подписи",
                new File(Objects.requireNonNull(getClass().getClassLoader().getResource("upload/Image_003.png")).getFile())
        ).body().asString();

        // Добавление документа к обращению
        DocumentInfo documentInfo =
                DocumentInfo
                        .builder()
                        .fileLink(pathFile)
                        .fileName("Image_002.png")
                        .fileSigLink(pathFileSig)
                        .fileSigName("Image_003.png")
                        .scale("100")
                        .build();
        String idDocument = lklFront.put(
                "/api" + putAddDocumentCase,
                pathParam,
                documentInfo,
                "Добавление документа к обращению"
        ).body().asString();
        response = lklFront.get(
                "/api" + getListSchematicDocuments,
                pathParam,
                "schemas/DocumentInfoArray.json",
                "Получение списка документов схем"
        );
        List<Map> listDocumentInfo = JsonPath.with(response.asString()).get("findAll {a -> a.id == '" + idDocument + "'}");
        DocumentInfo checkResponce = Util.GSON.fromJson(Util.GSON.toJsonTree(listDocumentInfo.get(0)), DocumentInfo.class);

        assertThat(checkResponce.getFileLink(), equalTo(pathFile));
        assertThat(checkResponce.getFileName(), equalTo("Image_002.png"));
        assertThat(checkResponce.getFileSigLink(), equalTo(pathFileSig));
        assertThat(checkResponce.getFileSigName(), equalTo("Image_002.png.sig"));
        assertThat(checkResponce.getKind(), equalTo(UNKNOWN.getName()));
        assertThat(checkResponce.getKindName(), equalTo("Не определен"));
        assertThat(checkResponce.getScale(), equalTo("100"));
        assertThat(checkResponce.getType(), equalTo(TypeEnum.SCHEME.getName()));

        // Сохранение лесохозяйственного объекта
        ForestObject forestObject = ForestObject
                .builder()
                .cuttingArea("465432")
                .quarter("01:010101")
                .taxationUnit("2245")
                .usageType(HARVESTINGWOOD.getName())
                .forestry(
                        DictionaryItem
                                .builder()
                                .code("1")
                                .title("Лесничество 1")
                                .build()
                )
                .harvestingWood(
                        HarvestingWoodInfo
                                .builder()
                                .area(BigDecimal.valueOf(66543))
                                .farm("hardwood")
                                .formCutting("selective")
                                .volume(BigDecimal.valueOf(5465))
                                .tree(
                                        DictionaryItem
                                                .builder()
                                                .code("2")
                                                .title("Порода 2")
                                                .build()
                                )
                                .typeCutting(
                                        DictionaryItem
                                                .builder()
                                                .code("1")
                                                .title("Вид рубки 1")
                                                .build()
                                )
                                .unitType(
                                        DictionaryItem
                                                .builder()
                                                .code("1")
                                                .title("Единицы измерения 1")
                                                .build()
                                )
                                .build()
                )
                .schemeDocument(documentInfo)
                .subforestry(
                        DictionaryItem
                                .builder()
                                .code("1")
                                .title("Участковое лесничество 1")
                                .build()
                )
                .tract(
                        DictionaryItem
                                .builder()
                                .code("1")
                                .title("Урочище 1")
                                .build()
                )
                .build();
        // TODO забыл добавить post запрос на сохранение значений forestObject

        // Получим id созданного лнсного объекта
        String idForestObgect = lklFront.get(
                postSaveForestryObject,
                pathParam,
                "schemas/ApplicationSaveResponse.json",
                "Сохранение лесохозяйственного объекта"
        ).body().as(ApplicationSaveResponse.class).getId();
        ForestObjectShortWithCount forestObjectShortWithCount = lklFront.get(
                getListForestryObjects,
                pathParam,
                queryParam,
                "schemas/ForestObjectShortWithCount.json",
                "Получение списка лесохозяйственных объектов"
        ).as(ForestObjectShortWithCount.class);
        assertThat(forestObjectShortWithCount.getItems().get(0).getArea(), equalTo(BigDecimal.valueOf(66543)));
//        assertThat(forestObjectShortWithCount.getItems().get(0).getCreateDate(), equalTo()); // TODO не понятная дата
        assertThat(forestObjectShortWithCount.getItems().get(0).getCuttingArea(), equalTo("465432"));
        assertThat(forestObjectShortWithCount.getItems().get(0).getForestryId(), equalTo("1"));
        assertThat(forestObjectShortWithCount.getItems().get(0).getForestryName(), equalTo("Лесничество 1"));
        assertThat(forestObjectShortWithCount.getItems().get(0).getId(), equalTo(idForestObgect));
        assertThat(forestObjectShortWithCount.getItems().get(0).getQuarter(), equalTo("01:010101"));
        assertThat(forestObjectShortWithCount.getItems().get(0).getSubforestryId(), equalTo("1"));
        assertThat(forestObjectShortWithCount.getItems().get(0).getSubforestryName(), equalTo("Участковое лесничество 1"));
        assertThat(forestObjectShortWithCount.getItems().get(0).getTaxationUnit(), equalTo("2245"));
        assertThat(forestObjectShortWithCount.getItems().get(0).getTractId(), equalTo("1"));
        assertThat(forestObjectShortWithCount.getItems().get(0).getTractName(), equalTo("Урочище 1"));
        assertThat(forestObjectShortWithCount.getItems().get(0).getUnitTypeId(), equalTo("1"));
        assertThat(forestObjectShortWithCount.getItems().get(0).getUnitTypeName(), equalTo("Единицы измерения 1"));
        assertThat(forestObjectShortWithCount.getItems().get(0).getUsageType(), equalTo(HARVESTINGWOOD.getName()));
        assertThat(forestObjectShortWithCount.getItems().get(0).getVolume(), equalTo(1));

        // Далее на 3 страницу
        lklFront.get(
                getListDocumentKindsTypes,
                "schemas/DictionaryItemArray.json",
                "Получение списка видов документов"
        );
        // TODO проверить документы, пришло 3 документа
        // [{"code":"schemeObject","title":"Схема размещения лесосеки, объекта лесной инфраструктуры, объекта лесоперерабатывающей инфраструктуры и объекта, не связанного с созданием лесной инфраструктуры"},{"code":"schemeHarvestingWood","title":"Общая схема расположения мест проведения работ при использовании лесов в целях заготовки древесины"},{"code":"idDocument","title":"Документ, удостоверяющий личность"}]
        response = lklFront.get(
                getUploadedApplicationDocuments,
                pathParam,
                "schemas/DocumentInfoArray.json",
                "Получение данных загруженных документов обращения"
        );
//        listDocumentInfo = new ArrayList<>();
        listDocumentInfo = JsonPath.with(response.asString()).get("findAll {a -> a.id == '" + idDocument + "'}");
        DocumentInfo checkResponce_2 = Util.GSON.fromJson(Util.GSON.toJsonTree(listDocumentInfo.get(0)), DocumentInfo.class);
        assertThat(checkResponce_2.getFileLink(), equalTo(checkResponce.getFileLink()));
        assertThat(checkResponce_2.getFileName(), equalTo(checkResponce.getFileName()));
        assertThat(checkResponce_2.getFileSigLink(), equalTo(checkResponce.getFileSigLink()));
        assertThat(checkResponce_2.getFileSigName(), equalTo(checkResponce.getFileSigName()));
        assertThat(checkResponce_2.getId(), equalTo(checkResponce.getId()));
        assertThat(checkResponce_2.getKind(), equalTo(checkResponce.getKind()));
        assertThat(checkResponce_2.getKindName(), equalTo(checkResponce.getKindName()));
        assertThat(checkResponce_2.getScale(), equalTo(checkResponce.getScale()));
        assertThat(checkResponce_2.getType(), equalTo(checkResponce.getType()));

        // Сформировать документ
        lklFront.get(
                getGeneratedDocuments,
                pathParam,
                "schemas/DocumentOutInfoArray.json",
                "Получение данных сформированных документов документов обращения"
        );
        // TODO получил пустой список, что должно быть?
        // TODO завершить формирование
    }


}
