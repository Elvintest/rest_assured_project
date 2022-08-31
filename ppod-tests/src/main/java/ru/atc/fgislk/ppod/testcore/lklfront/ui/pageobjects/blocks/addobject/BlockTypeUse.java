package ru.atc.fgislk.ppod.testcore.lklfront.ui.pageobjects.blocks.addobject;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import java.time.Duration;
import java.util.Random;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static ru.atc.fgislk.ppod.testcore.lklfront.ui.common.PagePrimitive.selectConboBox;

/**
 * Блок Вид использования
 */
public class BlockTypeUse {
    private final Random generator = new Random();
    /**
     * Вид использования
     */
    private final SelenideElement typeUse = $(By.xpath("//h3[text() = 'Вид использования']/../div[1]/div[1]//input"));

    /**
     * Площадь лесосеки
     */
    private final SelenideElement cuttingArea = $(By.xpath("//input[@name = 'harvestingWood.area']"));
    /**
     * Наименование объекта<br>
     * Для Создание/снос объектов лесной инфраструктуры
     */
    private final SelenideElement objectName = $(By.xpath("//h3[text() = 'Вид использования']/../div[2]/div[1]//input"));
    /**
     * Наименование объекта<br>
     * Создание/снос объектов, не связанных с созданием лесной инфраструктуры
     */
    private final SelenideElement objectNameOther = $(By.xpath("//h3[text() = 'Вид использования']/../div[2]/div[1]//input"));


    /**
     * Мероприятие<br>
     * Для Создание/снос объектов лесной инфраструктуры
     */
    private final SelenideElement event = $(By.xpath("//h3[text() = 'Вид использования']/../div[2]/div[2]//input"));
    /**
     * Мероприятие<br>
     * Для Создание/снос объектов, не связанных с созданием лесной инфраструктуры
     */
    private final SelenideElement eventOther = $(By.xpath("//h3[text() = 'Вид использования']/../div[2]/div[2]//input"));
    /**
     * Площадь объекта<br>
     * Для Создание/снос объектов лесной инфраструктуры
     */
    private final SelenideElement objectArea = $(By.xpath("//input[@name = 'harvestingObject.area']"));
    /**
     * Площадь объекта<br>
     * Для Создание/снос объектов лесной инфраструктуры
     */
    private final SelenideElement objectAreaOther = $(By.xpath("//input[@name = 'otherUsageObjects.area']"));
    /**
     * Виды заготавливаемых ресурсов
     */
    private final SelenideElement typesHarvestedesources = $(By.xpath("//h3[text() = 'Вид использования']/../div[2]/div[1]//input"));
    /**
     * Площадь используемого участка
     */
    private final SelenideElement usableArea = $(By.xpath("//input[@name = 'otherUsageTypes.area'"));

    /**
     * Единицы измерения
     */
    private final SelenideElement units = $(By.xpath("//h3[text() = 'Вид использования']/../div[2]/div[4]//input"));
    /**
     * Объем использования
     */
    private final SelenideElement scopeUse = $(By.id("otherUsageTypes.volume"));

    /**
     * Выбрать Вид использования
     *
     * @param value Вид использования
     */
    public void selectTypeUse(String value) {
        typeUse.find(By.xpath(".//preceding-sibling::div")).shouldBe(Condition.enabled).click();
        ElementsCollection listTypeUse = $$(By.xpath("//div[@id = 'menu-usageType']//li")).shouldBe(CollectionCondition.sizeNotEqual(0), Duration.ofSeconds(2));
        listTypeUse.findBy(Condition.text(value)).click();
    }

    /**
     * Ввести Площадь лесосеки
     *
     * @param value Площадь лесосеки
     */
    public void setCuttingArea(String value) {
        cuttingArea.shouldBe(Condition.visible).setValue(value);
    }

    /**
     * Выбрать наименование объекта<br>
     * Для Создание/снос объектов лесной инфраструктуры
     *
     * @param value наименование объекта
     */
    public void selectObjectName(String value) {
        ElementsCollection listObjectName = selectConboBox(objectName);
        listObjectName.findBy(Condition.text(value)).click();
    }
    /**
     * Выбрать наименование объекта<br>
     * Для Создание/снос объектов лесной инфраструктуры
     *
     */
    public void selectObjectName() {
        ElementsCollection listObjectName = selectConboBox(objectName);
        listObjectName.get(generator.nextInt(listObjectName.size())).click();
    }

    /**
     * Выбрать наименование объекта<br>
     * Для Создание/снос объектов, не связанных с созданием лесной инфраструктуры
     *
     * @param value наименование объекта
     */
    public void selectObjectNameOther(String value) {
        ElementsCollection listObjectNameOther = selectConboBox(objectNameOther);
        listObjectNameOther.findBy(Condition.text(value)).click();
    }
    /**
     * Выбрать наименование объекта<br>
     * Для Создание/снос объектов, не связанных с созданием лесной инфраструктуры
     */
    public void selectObjectNameOther() {
        ElementsCollection listObjectNameOther = selectConboBox(objectNameOther);
        listObjectNameOther.get(generator.nextInt(listObjectNameOther.size())).click();
    }

    /**
     * Выбрать мероприятие<br>
     * Для Создание/снос объектов лесной инфраструктуры
     *
     * @param value мероприятие
     */
    public void selectEvent(String value) {
        ElementsCollection listEvent = selectConboBox(event);
        listEvent.findBy(Condition.text(value)).click();
    }
    /**
     * Выбрать мероприятие<br>
     * Для Создание/снос объектов лесной инфраструктуры
     */
    public void selectEvent() {
        ElementsCollection listEvent = selectConboBox(event);
        listEvent.get(generator.nextInt(listEvent.size())).click();
    }

    /**
     * Выбрать мероприятие<br>
     * Создание/снос объектов, не связанных с созданием лесной инфраструктуры
     *
     * @param value мероприятие
     */
    public void selectEventOther(String value) {
        ElementsCollection listEventOther = selectConboBox(eventOther);
        listEventOther.findBy(Condition.text(value)).click();
    }
    /**
     * Выбрать мероприятие<br>
     * Создание/снос объектов, не связанных с созданием лесной инфраструктуры
     */
    public void selectEventOther() {
        ElementsCollection listEventOther = selectConboBox(eventOther);
        listEventOther.get(generator.nextInt(listEventOther.size())).click();
    }

    /**
     * Ввести Площадь объекта<br>
     * Для Создание/снос объектов лесной инфраструктуры
     *
     * @param value Площадь объекта
     */
    public void setObjectArea(String value) {
        objectArea.shouldBe(Condition.visible).setValue(value);
    }

    /**
     * Ввести Площадь объекта<br>
     * Для Создание/снос объектов, не связанных с созданием лесной инфраструктуры
     *
     * @param value Площадь объекта
     */
    public void setObjectAreaOther(String value) {
        objectAreaOther.shouldBe(Condition.visible).setValue(value);
    }

    /**
     * Выбрать вид заготавливаемых ресурсов<br>
     *
     * @param value название
     */
    public void selectTypesHarvestedesources(String value) {
        ElementsCollection listtypesHarvestedesources = selectConboBox(typesHarvestedesources);
        listtypesHarvestedesources.findBy(Condition.text(value)).click();
    }
    /**
     * Выбрать вид заготавливаемых ресурсов<br>
     */
    public void selectTypesHarvestedesources() {
        ElementsCollection listtypesHarvestedesources = selectConboBox(typesHarvestedesources);
        listtypesHarvestedesources.get(generator.nextInt(listtypesHarvestedesources.size())).click();
    }

    /**
     * Ввести площадь используемого участка
     *
     * @param value площадь используемого участка
     */
    public void setUsableArea(String value) {
        usableArea.shouldBe(Condition.visible).setValue(value);
    }

    /**
     * Выбрать единицы измерения
     *
     * @param value единицы измерения
     */
    public void selectUnits(String value) {
        ElementsCollection listunits = selectConboBox(units);
        listunits.findBy(Condition.text(value)).click();
    }

    /**
     * Ввести объем использования
     */
    public void setScopeUse(String value) {
        scopeUse.shouldBe(Condition.visible).setValue(value);
    }
}
