package ru.atc.fgislk.pup.testcore.enums;

import ru.atc.fgislk.shared.testcomponents.camunda.ICamundaTaskEnum;

/**
 * Перечисление определяет шаги в тестовом процессе
 */
public enum TestProcessStepsEnum implements ICamundaTaskEnum {
    /**
     * Инициализация initializeTaskDispatcher
     */
    INITIALIZE_TASK_DISPATCHER("initializeTaskDispatcher", null, "Инициализация"),
    /**
     * СТ отправки статуса о старте процесса
     */
    STATUS_STARTPROCESS("startProcess", null, "startProcess"),
    /**
     * СТ Подготовить задачу 1
     */
    ST_PREPARE_TASK_1("prepareTask1", null, "Подготовить задачу 1"),
    /**
     * СТ Сервис шага 1
     */
    STATUS_SERVICE_STEP_1("serviceStep1", null, "Сервис шага 1"),
    /**
     * ЮТ Задача пользователя 1. FormKey такойже, как id подготовительного сервис-таска
     */
    UT_USER_TASK_1("userTask1", "prepareTask1", "Задача пользователя 1"),
    /**
     * СТ Подготовить задачу 2
     */
    ST_PREPARE_TASK_2("prepareTask2", null, "Подготовить задачу 2"),
    /**
     * ЮТ Задача пользователя 2. FormKey такойже, как id подготовительного сервис-таска
     */
    UT_USER_TASK_2("userTask2", "prepareTask2", "Разрешить"),
    /**
     * СТ Подготовить задачу 3
     */
    ST_PREPARE_TASK_3("prepareTask3", null, "Подготовить задачу 3"),
    /**
     * ЮТ Задача пользователя 3. FormKey такойже, как id подготовительного сервис-таска
     */
    UT_USER_TASK_3("userTask3", "prepareTask3", "Не разрешить"),
    /**
     * старт
     */
    START_EVENT("StartEvent_1", null, null),
    /**
     * выход 1
     */
    EXIT_1("Event_exit1", null, "exit1"),
    /**
     * выход 1
     */
    EXIT_2("Event_exit2", null, "exit2"),
    /**
     * выход 1
     */
    EXIT_3("Event_exit3", null, "exit3"),
    /**
     * выход 1
     */
    EXIT_4("Event_exit4", null, "exit4"),
    /**
     * условный переход
     */
    GATEWAY_1("Gateway_1", null, null);


    private final String id;
    private final String formKey;
    private final String name;

    TestProcessStepsEnum(String id, String formKey, String name) {
        this.id = id;
        this.formKey = formKey;
        this.name = name;
    }

    @Override
    public String getActivityId() {
        return id;
    }

    @Override
    public String getFormKey() {
        return formKey;
    }

    @Override
    public String getActivityName() {
        return name;
    }
}
