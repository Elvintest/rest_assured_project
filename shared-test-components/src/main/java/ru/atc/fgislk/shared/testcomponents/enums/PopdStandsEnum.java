package ru.atc.fgislk.shared.testcomponents.enums;

/**
 * сервисы Подстистемы обработки пространственных данных
 * <p>
 * https://kb.at-consulting.ru/pages/viewpage.action?pageId=302861904
 */
public enum PopdStandsEnum {
    /**
     * DEV-стенд
     */
    DEV("http://load.popd.dev.fgislk.at-consulting.ru",
            "http://sa.popd.dev.fgislk.at-consulting.ru",
            "http://export.popd.dev.fgislk.at-consulting.ru",
            "http://reporting.popd.dev.fgislk.at-consulting.ru",
            "http://api.popd.dev.fgislk.at-consulting.ru",
            PostgreStandsEnum.DEV_POPD),
    UAT("http://load.popd.uat.fgislk.at-consulting.ru",
            "http://map-api.plk.uat.fgislk.at-consulting.ru",
            "http://export.popd.uat.fgislk.at-consulting.ru",
            "http://reporting.popd.uat.fgislk.at-consulting.ru",
            "http://api.popd.uat.fgislk.at-consulting.ru",
            PostgreStandsEnum.UAT_POPD);
    /**
     * Модуль внесения
     */
    final String loadService;
    /**
     * Модуль пространственного анализа
     */
    final String saService;
    /**
     * Модуль выгрузки пространственных данных
     */
    final String exportService;
    /**
     * Модуль выгрузки картографических данных для отчетных форм
     */
    final String reportingService;
    /**
     * Модуль API
     */
    final String apiService;

    /**
     * база данных подсистемы
     */
    final PostgreStandsEnum db;

    PopdStandsEnum(String loadService, String saService, String exportService, String reportingService, String apiService, PostgreStandsEnum db) {
        this.loadService = loadService;
        this.saService = saService;
        this.exportService = exportService;
        this.reportingService = reportingService;
        this.apiService = apiService;
        this.db = db;
    }

    public String getLoadService() {
        return loadService;
    }

    public String getSaService() {
        return saService;
    }

    public String getExportService() {
        return exportService;
    }

    public String getReportingService() {
        return reportingService;
    }

    public String getApiService() {
        return apiService;
    }

    public PostgreStandsEnum getDb() {
        return db;
    }
}
