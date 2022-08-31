package ru.atc.fgislk.ppod.testcore.phdfs;
// http://10.125.4.181:30007/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config#/application-details-controller/getAppVersionInfo

/**
 * файловое хранилище
 */
public class EndPoints {
    /**
     * GET Скачать файл из файлового хранилища
     */
    public final static String getDownloadFileStorage = "/{bucket}/{fileCode}/{fileType}";
    /**
     * POST Загрузить файл в файловое хранилище
     */
    public final static String postUploadFileStorage = "/{bucket}/{fileCode}/{fileType}";
    /**
     * POST Загрузить файл в файловое хранилище и получить его GUID и Digest
     */
    public final static String postUploanGuid = "/userupload";
    /**
     * Скачать файл по GUID из файлового хранилища
     */
    public final static String getDownloadGuid = "/userdownload/{guid}";
    /**
     * Получить FileLink по GUID
     */
    public final static String getFileLinkFromGuid = "/getLink";
    /**
     * Установить TTL файла
     */
    public final static String getSetTTL = "/{fileLink}/setTTL";
    /**
     * Получить метаданные файла
     */
    public final static String getMeta = "/{fileLink}/meta";
    /**
     * Существует ли файл в файловом хранилище
     */
    public final static String getExist = "/{fileLink}/exists";
    /**
     * Добавить в хранилище ссылку на внешний ресурс
     */
    public final static String getPutExtLink = "/putExtLink";
    /**
     * Получить временную ссылку для скачивания файла
     */
    public final static String getTmpDownloadLink = "/{fileLink}/genDownloadLink";
    /**
     * Получить файл по токену
     */
    public final static String getFileToken = "/download";
    /**
     * Получить FileLink по токену
     */
    public final static String getResolveDownloadLink = "/resolveDownloadLink";
    /**
     * Получить временную ссылку загрузки файла
     */
    public final static String getGenerateUploadLink = "/generateUploadLink";
    /**
     * Загрузить файл по временной ссылке
     */
    public final static String postUploadByToken = "/uploadByToken/{token}";
    /**
     * Получить файл по токену
     */
    public final static String getDownloadWithToken = "/uploadByToken";
    /**
     * Формирует архив из заданных файлов и запускает процедуру архивации
     */
    public final static String postCompress = "/compress";
    /**
     * Узнать статус операции архивации
     */
    public final static String getCompress = "/compress/{taskToken}";
    /**
     * Добавить в хранилище несколько ссылок на внешний ресурс
     */
    public final static String postPutExtLinks = "/putExtLinks";
    /**
     * Получение файла по внутренней ссылке
     */
    public final static String getFileInternalLinks = "/{internalLink}";
    /**
     * Разархивирует указанный архив
     */
    public final static String postDecompress = "/{containerCode}/{fileCode}/{fileType}/decompress";
    /**
     * Разархивирует указанный архив, путь в link
     */
    public final static String postDecompressPath = "/{link}/decompress";
    /**
     * Узнать статус операции разархивирования
     */
    public final static String getDecompress = "/decompress/{taskToken}";
    /**
     * Получить результат разархивирования
     */
    public static final String getDecompressOut = "/decompress/{taskToken}/output";


}
