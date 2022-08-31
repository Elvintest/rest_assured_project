package ru.atc.fgislk.ppod.testcore.lklback.enums;

public enum FarmEnum {
    SOFTWOOD("softwood"),
    HARDWOOD("hardwood"),
    CONIFEROUS("coniferous");

    private String name;

    FarmEnum(String value) {
        this.name = value;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return String.valueOf(name);
    }

    public static FarmEnum fromValue(String input) {
        for (FarmEnum b : FarmEnum.values()) {
            if (b.name.equals(input)) {
                return b;
            }
        }
        return null;
    }
}
