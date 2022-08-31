package ru.atc.fgislk.ppod.testcore.lklback;
// http://10.125.4.181:30001/swagger-ui/index.html#/
/**
 * End points
 */
public class EndPoints {
    // ------------------------ application-controller
    /**
     * DELETE Удаление лесохозяйственного объекта
     */
    public static final String deleteForestObject = "/LKL/application/object/{objectId}";
    /**
     * DELETE Сохранение документа обращения
     */
    public static final String saveCaseDocument = "/LKL/application/document/{documentId}";
    /**
     * GET Получение данных загруженных документов обращения
     */
    public static final String getUploadedApplicationDocuments = "/LKL/application/{applicationId}/documents";
    /**
     * GET Получение списка пакетов документов
     */
    public static final String getListDocumentPackage = "/LKL/applications";
    /**
     * GET Получение данных обращения
     */
    public static final String getCaseData = "/LKL/application/{applicationId}";
    /**
     * GET Получение xml обращения в формате base64
     */
    public static final String getXmlBase64 = "/LKL/application/{applicationId}";
    /**
     * GET Получение данных сформированных документов документов обращения
     */
    public static final String getGeneratedDocuments = "/LKL/application/{applicationId}/out/documents";
    /**
     * GET Получение списка лесохозяйственных объектов
     */
    public static final String getListForestryObjects = "/LKL/application/{applicationId}/objects";
    /**
     * GET Получение истории изменения статусов обращения
     */
    public static final String getHistoryStatusChanges = "/LKL/application/{applicationId}/history";
    /**
     * GET Получение списка документов схем
     */
    public static final String getListSchematicDocuments = "/LKL/application/{applicationId}/documents/scheme";
    /**
     * GET Получение данных лесохозяйственного объекта
     */
    public static final String getForestObjectData = "/LKL/application/object/{objectId}";
    /**
     * POST Сохранение черновика обращения
     */
    public static final String postSavingDraftCase = "/LKL/application";
    /**
     * POST Получение xml обращения в формате base64
     */
    public static final String postXmlBase64 = "/LKL/application/{applicationId}/send";
    /**
     * POST Сохранение подписи xml файла
     */
    public static final String postSaveSignatureXml = "/LKL/application/{applicationId}/saveSignature";
    /**
     * POST Сохранение лесохозяйственного объекта
     */
    public static final String postSaveForestryObject = "/LKL/application/{applicationId}/object";
    /**
     * POST Сохранение документов обращения
     */
    public static final String postSaveAppealDocuments = "/LKL/application/{applicationId}/documents";
    /**
     * POST Сохранение документа обращения
     */
    public static final String postSaveAppealDocument = "/LKL/application/{applicationId}/document";
    /**
     * POST Копирование данных объекта
     */
    public static final String postCopyObjectData = "/LKL/application/object/{objectId}/copy";
    /**
     * PUT Добавление документа к обращению
     */
    public static final String putAddDocumentCase = "/LKL/application/{applicationId}/document/scheme";

    // ------------------------ file-controller
    /**
     * DELETE Удаление файла
     */
    public static final String deleteFile = "/LKL/file/deleteFile/{container}/{fileCode}/{fileType}";
    /**
     * GET Скачивание файла
     */
    public static final String getDownloadFile = "/LKL/file/download/{container}/{fileCode}/{fileType}";
    /**
     * POST Загрузка файла
     */
    public static final String postUploadFile = "/LKL/file/upload";

    // ------------------------ ref-controller
    /**
     * GET Получение списка статусов пакетов документов
     */
    public static final String getListDocumentBatchStatuses = "/LKL/ref/statuses";
    /**
     * GET Получение списка видов использования леса
     */
    public static final String getListForestUses = "/LKL/ref/objectUsageType";
    /**
     * GET Получение списка типов пакетов документов
     */
    public static final String getListDocumentBatchTypes = "/LKL/ref/kinds";
    /**
     * GET Получение списка видов рубки
     */
    public static final String getListFellingTypes = "/LKL/ref/formCutting";
    /**
     * GET Получение списка типов хозяйств
     */
    public static final String getListFarmTypes = "/LKL/ref/farm";
    /**
     * GET Получение списка типов документов
     */
    public static final String getListDocumentTypes = "/LKL/ref/documentTypes";
    /**
     * GET Получение списка видов документов
     */
    public static final String getListDocumentKindsTypes = "/LKL/ref/documentKinds";


}
