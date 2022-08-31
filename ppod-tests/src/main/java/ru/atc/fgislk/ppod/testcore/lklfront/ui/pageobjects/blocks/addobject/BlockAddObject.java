package ru.atc.fgislk.ppod.testcore.lklfront.ui.pageobjects.blocks.addobject;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import ru.atc.fgislk.ppod.testcore.lklfront.ui.pageobjects.blocks.formationdocument.FormAttachment;

import java.time.Duration;
import java.util.Random;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static ru.atc.fgislk.ppod.testcore.lklfront.ui.common.PagePrimitive.selectConboBox;

/**
 * Блок добавление объекта
 */
public class BlockAddObject {
    private final Random generator = new Random();
    /**
     * Форма загрузки схемы объекта
     */
    public FormAttachment formAttachment = new FormAttachment();
    /**
     * Лесничество
     */
    private final SelenideElement forestry = $(By.xpath("//h3[text() = 'Добавление объекта']/../div[1]/div[1]//input"));
    /**
     * Участковое лесничество
     */
    private final SelenideElement districtForestry = $(By.xpath("//h3[text() = 'Добавление объекта']/../div[1]/div[2]//input"));
    /**
     * Урочище
     */
    private final SelenideElement tract = $(By.xpath("//h3[text() = 'Добавление объекта']/../div[1]/div[3]//input"));
    /**
     * Квартал
     */
    private final SelenideElement quarter = $(By.xpath("//input[@name = 'declarationObjectData.quarter']"));
    /**
     * Выдел
     */
    private final SelenideElement selection = $(By.xpath("//input[@name = 'declarationObjectData.taxationUnit']"));
    /**
     * Номер лесосеки
     */
    private final SelenideElement cuttingNumber = $(By.xpath("//input[@name = 'declarationObjectData.cuttingArea']"));
    /**
     * Загрузить схему объекта
     */
    private final SelenideElement downloadObjectScheme = $(By.xpath("//button[text() = 'Загрузить схему объекта']"));

    /**
     * Выбрать лесничество
     *
     * @param value название субъекта
     */
    public void selectForestry(String value) {
        selectConboBox(forestry, "menu-declarationObjectData.forestryCode").findBy(Condition.text(value)).click();
    }

    /**
     * Выбрать лесничество
     */
    public void selectForestry() {
        ElementsCollection listForestry = selectConboBox(forestry, "menu-declarationObjectData.forestryCode");
        listForestry.get(generator.nextInt(listForestry.size())).click();
    }

    /**
     * Выбрать участковое лесничество
     *
     * @param value название субъекта
     */
    public void selectDistrictForestry(String value) {
        ElementsCollection listDistrictForestry = selectConboBox(districtForestry, "menu-declarationObjectData.subforestryCode");
        listDistrictForestry.findBy(Condition.text(value)).click();
    }

    /**
     * Выбрать участковое лесничество
     */
    public void selectDistrictForestry() {
        ElementsCollection listDistrictForestry = selectConboBox(districtForestry, "menu-declarationObjectData.subforestryCode");
        listDistrictForestry.get(generator.nextInt(listDistrictForestry.size())).click();
    }

    /**
     * Выбрать Урочище
     *
     * @param value название субъекта
     */
    public void selectTract(String value) {
        ElementsCollection listTract = selectConboBox(tract, "menu-declarationObjectData.tractCode");
        listTract.findBy(Condition.text(value)).click();
    }

    /**
     * Выбрать Урочище
     */
    public void selectTract() {
        ElementsCollection listTract = selectConboBox(tract, "menu-declarationObjectData.tractCode");
        listTract.get(generator.nextInt(listTract.size())).click();
    }

    /**
     * Ввести квартал
     *
     * @param value номер квартала
     */
    public void setQuarter(String value) {
        quarter.shouldBe(Condition.enabled).setValue(value);
    }

    /**
     * Ввести выдел
     *
     * @param value номер выдела
     */
    public void setSelection(String value) {
        selection.shouldBe(Condition.enabled).setValue(value);
    }

    /**
     * Ввести номер лесосеки
     *
     * @param value номер лесосеки
     */
    public void setCuttingNumber(String value) {
        cuttingNumber.shouldBe(Condition.enabled).setValue(value);
    }

    /**
     * Назжать на ссылку Загрузить схему объекта
     */
    public void clickDownloadObjectScheme() {
        downloadObjectScheme.shouldBe(Condition.enabled).shouldBe(Condition.visible).click();
    }
}
