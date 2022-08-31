package ru.atc.fgislk.ppod.testcore.indocjournal.enums;

public enum StatusEnum {
    UNKNOWN("unknown"),
    ACTIVE("active"),
    CLOSING("closing"),
    CLOSED("closed");

    private final String name;

    StatusEnum(String name) {
        this.name = name;
    }

    public String getValue() {
        return name;
    }

    @Override
    public String toString() {
        return String.valueOf(name);
    }

    public static StatusEnum fromValue(String input) {
        for (StatusEnum b : StatusEnum.values()) {
            if (b.name.equals(input)) {
                return b;
            }
        }
        return null;
    }

}
