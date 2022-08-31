package ru.atc.fgislk.ppod.testcore.lklback.enums;

public enum TypeEnum {
    SCHEME("scheme"),
    FILE("file");

    private String name;

    TypeEnum(String value) {
        this.name = value;
    }
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return String.valueOf(name);
    }
    public static TypeEnum fromValue(String input) {
        for (TypeEnum b : TypeEnum.values()) {
            if (b.name.equals(input)) {
                return b;
            }
        }
        return null;
    }

}
