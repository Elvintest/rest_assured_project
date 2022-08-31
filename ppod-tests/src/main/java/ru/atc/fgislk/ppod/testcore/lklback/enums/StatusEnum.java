package ru.atc.fgislk.ppod.testcore.lklback.enums;

public enum StatusEnum {
    DRAFT("draft"),
    FORMED("formed"),
    SIGNED("signed"),
    SENT("sent"),
    UNKNOWN("unknown");

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
