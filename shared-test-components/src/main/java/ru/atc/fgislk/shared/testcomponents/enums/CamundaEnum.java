package ru.atc.fgislk.shared.testcomponents.enums;

public enum CamundaEnum {
    DEV("http://camunda.pup.dev.fgislk.at-consulting.ru/engine-rest/engine/default",
            "http://camunda.pup.dev.fgislk.at-consulting.ru/camunda/app/cockpit/default/#/processes",
            "demo",
            "demo",
            "http://camunda.pup.dev.fgislk.at-consulting.ru/camunda-heartbeat"),
    UAT("http://10.125.6.210:30400/engine-rest/engine/default",
            "http://10.125.6.210:30400/camunda/app/cockpit/default/#/processes",
            "demo",
            "demo",
            "http://10.125.6.210:30400/camunda-heartbeat");

    /**
     * адрес движка camunda
     */
    final String engine;
    /**
     * camunda cockpit
     */
    final String cockpit;
    /**
     * пользователь
     */
    final String login;
    /**
     * пароль
     */
    final String password;

    final String heartbeat;

    CamundaEnum(String engine, String cockpit, String login, String password, String hesrtbeat) {
        this.engine = engine;
        this.cockpit = cockpit;
        this.login = login;
        this.password = password;
        this.heartbeat = hesrtbeat;
    }

    /**
     * получить адрес движка camunda
     *
     * @return адрес camunda engine для выполнения REST-запросов
     */
    public String getEngine() {
        return engine;
    }

    public String getCockpit() {
        return cockpit;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getHeartbeat() {
        return heartbeat;
    }
}
