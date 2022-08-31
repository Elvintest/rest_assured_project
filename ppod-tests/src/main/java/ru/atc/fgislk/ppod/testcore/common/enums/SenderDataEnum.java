package ru.atc.fgislk.ppod.testcore.common.enums;

/**
 * Типы отправителей
 */
public enum SenderDataEnum {
    PERSON("person", "Физическое лицо"),
    BUSINESSMAN("businessman", "Индивидуальный предприниматель"),
    ORGANIZATION("organization", "Юридическое лицо"),
    FOREIGN_ORGANIZATION("foreignOrganization", "Иностранное юридическое лицо");

    final String value;
    final String name;

    SenderDataEnum(String value, String name) {
        this.value = value;
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
