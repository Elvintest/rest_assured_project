package ru.atc.fgislk.ppod.testcore.lklback;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.atc.fgislk.shared.testcomponents.back.BaseRestService;
import ru.atc.fgislk.shared.testcomponents.enums.StendsDescriptionEnum;

import java.util.Map;

import static ru.atc.fgislk.ppod.testcore.lklback.EndPoints.*;

public class LklBack extends BaseRestService {
    public LklBack(StendsDescriptionEnum stend) {
        super(stend.getPpodLk().getLklBack());
    }

    @Step("Получение данных загруженных документов обращения")
    public Response getUploadedApplicationDocuments(Map<String, String> pathParams) {
        return get(
                getUploadedApplicationDocuments,
                pathParams,
                "schemas/lklback/DocumentInfoArray.json",
                "Получение данных загруженных документов обращения");
    }

    @Step("Получение данных загруженных документов обращения без параметрами")
    public Response getListDocumentPackage() {
        return getListDocumentPackage(null);
    }

    @Step("Получение данных загруженных документов обращения с параметрами")
    public Response getListDocumentPackage(Map<String, String> queryParams) {
        Response response;
        if (queryParams != null)
            response  = get(
                getListDocumentPackage,
                null,
                queryParams,
                "schemas/lklback/ApplicationsInfoWithCount.json",
                "Получение данных загруженных документов обращения с параметрами");
        else
            response = get(
                    getListDocumentPackage,
                    "schemas/lklback/ApplicationsInfoWithCount.json",
                    "Получение данных загруженных документов обращения без параметрами");
        return response;
    }

    @Step("Получение данных обращения")
    public Response getCaseData(Map<String, String> pathParams) {
        return get(
                EndPoints.getCaseData,
                pathParams,
                "schemas/lklback/ApplicationInfo.json",
                "Получение данных обращения");
    }

    @Step("Получение xml обращения в формате base64")
    public Response getXmlBase64(Map<String, String> pathParams) {
        return get(
                EndPoints.getXmlBase64,
                pathParams,
                "schemas/lklback/XmlResponse.json",
                "Получение xml обращения в формате base64");
    }

    @Step("Получение данных сформированных документов документов обращения")
    public Response getGeneratedDocuments(Map<String, String> pathParams) {
        return get(
                EndPoints.getGeneratedDocuments,
                pathParams,
                "schemas/lklback/DocumentOutInfoArray.json",
                "Получение данных сформированных документов документов обращения");
    }

    @Step("Получение списка лесохозяйственных объектов")
    public Response getListForestryObjects(Map<String, String> pathParams) {
        return get(
                EndPoints.getListForestryObjects,
                pathParams,
                "schemas/lklback/ForestObjectShortWithCount.json",
                "Получение списка лесохозяйственных объектов");
    }

    @Step("Получение истории изменения статусов обращения")
    public Response getHistoryStatusChanges(Map<String, String> pathParams) {
        return get(
                getHistoryStatusChanges,
                pathParams,
                "schemas/lklback/StatusHistoryArray.json",
                "Получение истории изменения статусов обращения");
    }

    @Step("Получение списка документов схем")
    public Response getListSchematicDocuments(Map<String, String> pathParams) {
        return get(
                getListSchematicDocuments,
                pathParams,
                "schemas/lklback/DocumentInfoArray.json",
                "Получение списка документов схем");
    }

    @Step("Получение данных лесохозяйственного объекта")
    public Response getForestObjectData(Map<String, String> pathParams) {
        return get(
                getForestObjectData,
                pathParams,
                "schemas/lklback/ForestObject.json",
                "Получение данных лесохозяйственного объекта");

    }
}
