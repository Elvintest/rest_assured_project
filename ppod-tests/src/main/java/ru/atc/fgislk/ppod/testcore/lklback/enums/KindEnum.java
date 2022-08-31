package ru.atc.fgislk.ppod.testcore.lklback.enums;

public enum KindEnum {
    SCHEMEOBJECT("schemeObject"),
    SCHEMEHARVESTINGWOOD("schemeHarvestingWood"),
    IDDOCUMENT("idDocument"),
    UNKNOWN("unknown");

    private String name;

    KindEnum(String value) {
        this.name = value;
    }
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return String.valueOf(name);
    }
    public static KindEnum fromValue(String input) {
        for (KindEnum b : KindEnum.values()) {
            if (b.name.equals(input)) {
                return b;
            }
        }
        return null;
    }
}
