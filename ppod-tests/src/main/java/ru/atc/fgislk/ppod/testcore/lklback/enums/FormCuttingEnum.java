package ru.atc.fgislk.ppod.testcore.lklback.enums;

public enum FormCuttingEnum {
    CLEAR("clear"),
    SELECTIVE("selective");

    private String name;

    FormCuttingEnum(String value) {
        this.name = value;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return String.valueOf(name);
    }

    public static FormCuttingEnum fromValue(String input) {
        for (FormCuttingEnum b : FormCuttingEnum.values()) {
            if (b.name.equals(input)) {
                return b;
            }
        }
        return null;
    }
}
