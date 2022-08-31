package ru.atc.fgislk.shared.testcomponents.camunda;

/**
 * интерфейс для task камунды
 */
public interface ICamundaTaskEnum {
    /**
     * идентификатор таска
     * @return строка
     */
    String getActivityId();

    /**
     * Form Key если есть
     * @return строка
     */
    String getFormKey();

    /**
     * Название таска
     * @return строка
     */
    String getActivityName();
}
