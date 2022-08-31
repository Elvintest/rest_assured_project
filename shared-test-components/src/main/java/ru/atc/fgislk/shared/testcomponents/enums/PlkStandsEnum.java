package ru.atc.fgislk.shared.testcomponents.enums;

public enum PlkStandsEnum {
    DEV("http://auth.plk.dev.fgislk.at-consulting.ru",
            "http://map-api.plk.dev.fgislk.at-consulting.ru",
            "http://map.plk.dev.fgislk.at-consulting.ru/",
            "http://control.plk.dev.fgislk.at-consulting.ru",
            PostgreStandsEnum.DEV_POPD),
    UAT("http://auth.plk.uat.fgislk.at-consulting.ru",
            "http://map-api.plk.uat.fgislk.at-consulting.ru",
            "http://map.plk.uat.fgislk.at-consulting.ru",
            "http://control.plk.uat.fgislk.at-consulting.ru",
            PostgreStandsEnum.UAT_POPD);

    /**
     * Сервис авторизации
     */
    final String authApi;
    /**
     * Сервис API карты
     */
    final String mapApi;

    /**
     * пользовательский интерфейс
     */
    final String mapFront;
    /**
     * API Управления
     */
    final String controlApi;
    /**
     * база данных подсистемы
     */
    final PostgreStandsEnum db;


    PlkStandsEnum(String authApi, String mapApi, String mapFront, String controlApi, PostgreStandsEnum db) {
        this.authApi = authApi;
        this.mapApi = mapApi;
        this.mapFront = mapFront;
        this.controlApi = controlApi;
        this.db = db;
    }

    public String getAuthApi() {
        return authApi;
    }

    public String getMapApi() {
        return mapApi;
    }

    public String getMapFront() {
        return mapFront;
    }

    public String getControlApi() {
        return controlApi;
    }
}
