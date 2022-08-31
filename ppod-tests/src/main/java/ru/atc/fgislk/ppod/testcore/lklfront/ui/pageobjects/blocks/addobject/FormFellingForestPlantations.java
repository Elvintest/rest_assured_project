package ru.atc.fgislk.ppod.testcore.lklfront.ui.pageobjects.blocks.addobject;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import java.util.Random;

import static com.codeborne.selenide.Selenide.$;
import static ru.atc.fgislk.ppod.testcore.lklfront.ui.common.PagePrimitive.selectConboBox;

/**
 * Форма Рубка лесных насаждений
 */
public class FormFellingForestPlantations {
    private final Random generator = new Random();
    /**
     * Хозяйство
     */
    private final SelenideElement farm = $(By.xpath("(//h3[text() = 'Рубка лесных насаждений'])[2]/../following-sibling::main/div[1]/div[1]//input"));

    /**
     * Порода
     */
    private final SelenideElement breed = $(By.xpath("(//h3[text() = 'Рубка лесных насаждений'])[2]/../following-sibling::main/div[1]/div[2]//input"));

    /**
     * Вид рубки
     */
    private final SelenideElement fellingType = $(By.xpath("(//h3[text() = 'Рубка лесных насаждений'])[2]/../following-sibling::main/div[1]/div[3]//input"));
    /**
     * Форма рубки
     */
    private final SelenideElement fellingShape = $(By.xpath("(//h3[text() = 'Рубка лесных насаждений'])[2]/../following-sibling::main/div[1]/div[4]//input"));
    /**
     * Объем
     */
    private final SelenideElement volume = $(By.xpath("//input[@name = 'volume']"));
    /**
     * Площадь
     */
    private final SelenideElement area = $(By.xpath("//input[@name = 'area']"));
    /**
     * Единицы измерения
     */
    private final SelenideElement unit = $(By.xpath("(//h3[text() = 'Рубка лесных насаждений'])[2]/../following-sibling::main/div[1]/div[6]//input"));
    /**
     * Кнопка Сохранить
     */
    private final SelenideElement buttonSave = $(By.xpath("//footer/button[text() = 'сохранить']"));
    /**
     * Кнопка Отмена
     */
    private final SelenideElement buttonCancel = $(By.xpath("//footer/button[text() = 'Отмена']"));

    /**
     * Выбрать случайное хозяйство
     */
    public void selectFarm() {
        ElementsCollection listFarm = selectConboBox(farm, "menu-farmCode");
        listFarm.get(generator.nextInt(listFarm.size())).click();
    }

    /**
     * Выбрать хозяйство
     */
    public void selectFarm(String value) {
        ElementsCollection listFarm = selectConboBox(farm, "menu-farmCode");
        listFarm.findBy(Condition.text(value)).click();
    }

    /**
     * Выбрать случайная порода
     */
    public void selectBreed() {
        ElementsCollection listBreed = selectConboBox(breed);
        listBreed.get(generator.nextInt(listBreed.size())).click();
    }

    /**
     * Выбрать породу
     */
    public void selectBreed(String value) {
        ElementsCollection listBreed = selectConboBox(breed);
        listBreed.findBy(Condition.text(value)).click();
    }

    /**
     * Выбрать случайная вид рубки
     */
    public void selectFellingType() {
        ElementsCollection listFellingType = selectConboBox(fellingType);
        listFellingType.get(generator.nextInt(listFellingType.size())).click();
    }

    /**
     * Выбрать вид рубки
     */
    public void selectFellingType(String value) {
        ElementsCollection listFellingType = selectConboBox(fellingType);
        listFellingType.findBy(Condition.text(value)).click();
    }

    /**
     * Выбрать случайная форма рубки
     */
    public void selectFellingShape() {
        ElementsCollection listFellingShape = selectConboBox(fellingShape, "menu-formCuttingCode");
        listFellingShape.get(generator.nextInt(listFellingShape.size())).click();
    }

    /**
     * Выбрать форма рубки
     */
    public void selectFellingShape(String value) {
        ElementsCollection listFellingShape = selectConboBox(fellingShape, "menu-formCuttingCode");
        listFellingShape.findBy(Condition.text(value)).click();
    }

    /**
     * Ввести Объем
     *
     * @param value объем
     */
    public void setVolume(String value) {
        volume.setValue(value);
    }

    /**
     * Ввести площадь
     *
     * @param value площадь
     */
    public void setArea(String value) {
        area.setValue(value);
    }

    /**
     * Нажать кнопку Сохранить
     */
    public void clickButtonSave() {
        buttonSave.click();
    }

    /**
     * Нажать кнопку Отмена
     */
    public void clickButtonCancel() {
        buttonCancel.click();
    }

    /**
     * Выбрать случайную единицу измерения
     */
    public void selectUnit() {
        ElementsCollection listUnits = selectConboBox(unit, "menu-unitTypeCode");
        listUnits.get(generator.nextInt(listUnits.size())).click();
    }

    /**
     * Выбрать единицу измерения
     */
    public void selectUnit(String value) {
        ElementsCollection listUnits = selectConboBox(unit, "menu-unitTypeCode");
        listUnits.findBy(Condition.text(value)).click();
    }
}
