package ru.atc.fgislk.ppod.testcore.protocolRegistry;

// http://protocol-registry.ppod.dev.fgislk.at-consulting.ru/swagger-ui/index.html

/**
 * Сервис protocol-registry
 */
public class EndPoints {
    // ------------------------ protocol
    /**
     * Post создать протокол, получить данные о протоколе, закрыть протокол
     */
    public static final String postProtocol = "/protocol";

    public static final String getProtocol = "/protocol/{protocolId}";

    public static final String postCloseProtocol = "/protocol/{protocolId}/close";
    // ------------------------ control-task
    /**
     * Post Добавить задачу к протоколу
     */
    public static final String postaddTask = "/protocol/{protocolId}/control-task";
    // ------------------------ task-result controller
    /**
     * Post Добавить результат задаче
     */
    public static final String putTaskResult = "/protocol/{protocolId}/control-task/{taskId}/result";

}
