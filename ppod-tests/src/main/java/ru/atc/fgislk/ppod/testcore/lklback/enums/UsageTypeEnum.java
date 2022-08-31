package ru.atc.fgislk.ppod.testcore.lklback.enums;

public enum UsageTypeEnum {
    HARVESTINGWOOD("harvestingWood", "Объем использования лесов в целях заготовки древесины и/или живицы"),
    HARVESTINGOBJECT("harvestingObject", "Создание/снос объектов лесной инфраструктуры"),
    OTHERUSAGETYPES("otherUsageTypes", "Объем использования лесов в целях, не связанных с заготовкой древесины и/или живицы"),
    OTHERUSAGEOBJECTS("otherUsageObjects", "Создание/снос объектов, не связанных с созданием лесной инфраструктуры");

    private final String name;
    private final String title;

    UsageTypeEnum(String name, String title) {
        this.name = name;
        this.title = title;
    }
    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return String.valueOf(name);
    }
    public static UsageTypeEnum fromValue(String input) {
        for (UsageTypeEnum b : UsageTypeEnum.values()) {
            if (b.name.equals(input)) {
                return b;
            }
        }
        return null;
    }
}
