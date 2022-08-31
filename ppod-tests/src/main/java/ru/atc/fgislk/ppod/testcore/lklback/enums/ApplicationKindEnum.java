package ru.atc.fgislk.ppod.testcore.lklback.enums;

public enum ApplicationKindEnum {
    DECLARATION("declaration");

    private final String name;

    ApplicationKindEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static ApplicationKindEnum fromValue(String input) {
        for (ApplicationKindEnum b : ApplicationKindEnum.values()) {
            if (b.name.equals(input)) {
                return b;
            }
        }
        return null;
    }


}
