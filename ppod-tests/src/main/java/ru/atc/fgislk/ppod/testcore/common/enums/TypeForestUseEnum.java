package ru.atc.fgislk.ppod.testcore.common.enums;

/**
 * Справочник вида использования лесов
 */
public enum TypeForestUseEnum {
    HARVESTING_WOOD("harvestingWood", "Объем использования лесов в целях заготовки древесины"),
    HARVESTING_GALIPOT("harvestingGalipot", "Объем использования лесов в целях заготовки живицы"),
    HARVESTING_WOOD_AND_GALIPOT("harvestingWoodAndGalipot", "Объем использования лесов в целях заготовки древесины и живицы"),
    HARVESTING_OBJECT("harvestingObject", "Создание/снос объектов лесной инфраструктуры"),
    OTHER_USAGE_TYPES("otherUsageTypes", "Объем использования лесов в целях, не связанных с заготовкой древесины и/или живицы"),
    OTHER_USAGE_OBJECTS("otherUsageObjects", "Создание/снос объектов, не связанных с созданием лесной инфраструктуры");

    /**
     * Значение вида использования лесов
     */
    final String value;
    /**
     * Название вида использования лесов
     */
    final String name;

    TypeForestUseEnum(String value, String name) {
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
