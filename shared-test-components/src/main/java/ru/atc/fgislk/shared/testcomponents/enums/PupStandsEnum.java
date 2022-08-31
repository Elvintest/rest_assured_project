package ru.atc.fgislk.shared.testcomponents.enums;

public enum PupStandsEnum {
    DEV(            "http://control-process-proxy.pup.dev.fgislk.at-consulting.ru/control-process-proxy",
            "http://pup-task-dispatcher.pup.dev.fgislk.at-consulting.ru/task-dispatcher"),
    UAT("http://control-process-proxy.pup.dev.fgislk.at-consulting.ru/control-process-proxy",
            "http://pup-task-dispatcher.pup.dev.fgislk.at-consulting.ru/task-dispatcher");

    /**
     * Модуль "Консоль управления" swagger
     */
    final String controlProcessProxy;
    /**
     * Модуль "Диспетчер задач"
     */
    final String taskDispatcherApp;

    PupStandsEnum(String controlProcessProxy, String taskDispatcherApp) {
        this.controlProcessProxy = controlProcessProxy;
        this.taskDispatcherApp = taskDispatcherApp;
    }

    /**
     * Получить адрес Модуль "Консоль управления" swagger
     *
     * @return API
     */
    public String getControlProcessProxy() {
        return controlProcessProxy;
    }

    /**
     * Получить адрес Модуль "Диспетчер задач"
     *
     * @return API
     */
    public String getTaskDispatcherApp() {
        return taskDispatcherApp;
    }
}
