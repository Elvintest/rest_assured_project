package ru.atc.fgislk.ppod.testcore.lklfront.ui.pageobjects.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import ru.atc.fgislk.ppod.testcore.lklfront.ui.pageobjects.blocks.addobject.BlockAddObject;
import ru.atc.fgislk.ppod.testcore.lklfront.ui.pageobjects.blocks.addobject.BlockFellingForestPlantations;
import ru.atc.fgislk.ppod.testcore.lklfront.ui.pageobjects.blocks.addobject.BlockObjectCoordinates;
import ru.atc.fgislk.ppod.testcore.lklfront.ui.pageobjects.blocks.addobject.BlockTypeUse;

import java.util.Random;

import static com.codeborne.selenide.Selenide.$;

/**
 * Страница Добавление объекта
 */
public class PageAddObject {
    private Random generator;

    /**
     * Блок Добавление объекта
     */
    public BlockAddObject blockAddObject;
    /**
     * Блок Координаты объекта
     */
    public BlockObjectCoordinates blockObjectCoordinates;
    /**
     * Блок Вид использования
     */
    public BlockTypeUse blockTypeUse;
    /**
     * Блок Рубка лесных насаждений
     */
    public BlockFellingForestPlantations blockFellingForestPlantations;
    /**
     * Кнопка Назад
     */
    private final SelenideElement buttonBack = $(By.xpath("//button[text() = 'назад']"));
    /**
     * Кнопка Сохранить
     */
    private final SelenideElement buttonSave = $(By.xpath("//button[text() = 'сохранить']"));

    public PageAddObject() {
        this.generator = new Random();
        this.blockAddObject = new BlockAddObject();
        this.blockObjectCoordinates = new BlockObjectCoordinates();
        this.blockTypeUse = new BlockTypeUse();
        this.blockFellingForestPlantations = new BlockFellingForestPlantations();
    }

    /**
     * Нажать кнопку Назад
     */
    public void clickButtonBack(){
        buttonBack.shouldBe(Condition.enabled).click();
    }

    /**
     * Нажать кнопку Сохранить
     */
    public void clickButtonSave(){
        buttonSave.shouldBe(Condition.enabled).click();
    }


}
