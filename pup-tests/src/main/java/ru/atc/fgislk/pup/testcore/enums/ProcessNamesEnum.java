package ru.atc.fgislk.pup.testcore.enums;

import ru.atc.fgislk.shared.testcomponents.camunda.ICamundaProcessEnum;

/**
 * перечисление определяет тестовые процессы
 */
public enum ProcessNamesEnum implements ICamundaProcessEnum {

    GEO_TEST_1("ГЕО Тест 1 простой процесс", "geo-test-process1");

    private final String processName;
    private final String key;


    ProcessNamesEnum(String name, String key) {
        processName = name;
        this.key = key;
    }
    @Override
    public String getName() {
        return processName;
    }

    @Override
    public String getKey() {
        return key;
    }
}
