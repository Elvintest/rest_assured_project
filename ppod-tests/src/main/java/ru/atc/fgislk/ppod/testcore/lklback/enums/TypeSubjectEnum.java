package ru.atc.fgislk.ppod.testcore.lklback.enums;

public enum TypeSubjectEnum {
    PARTNER("partner"),
    SIGNER("signer"),
    UNKNOWN("unknown");

    private String name;

    TypeSubjectEnum(String value) {
        this.name = value;
    }
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return String.valueOf(name);
    }
    public static TypeSubjectEnum fromValue(String input) {
        for (TypeSubjectEnum b : TypeSubjectEnum.values()) {
            if (b.name.equals(input)) {
                return b;
            }
        }
        return null;
    }
}
