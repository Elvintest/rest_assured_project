package ru.atc.fgislk.ppod.testcore.common.enums;

/**
 * Типы лесопользователей
 */
public enum TypeForestUsers {
    PERSON("person", "Физическое лицо"),
    ORG("org", "Юридическое лицо"),
    IP("ip", "ИП");
    final String value;
    final String name;

    TypeForestUsers(String value, String name) {
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
