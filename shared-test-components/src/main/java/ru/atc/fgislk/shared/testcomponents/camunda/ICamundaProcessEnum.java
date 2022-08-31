package ru.atc.fgislk.shared.testcomponents.camunda;

/**
 * интерфейс для процессов в камунде
 */
public interface ICamundaProcessEnum {
    /**
     * название процесса
     * @return строка
     */
    String getName();

    /**
     * ключ процесса
     * @return строка
     */
    String getKey();
}
