package ru.atc.fgislk.ppod.testcore.lklfront.ui.pageobjects.blocks.addobject;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import java.util.Random;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

/**
 * Блок Рубка лесных насаждений
 */
public class BlockFellingForestPlantations {
    /**
     * Форма Рубка лесных насаждений
     */
    public FormFellingForestPlantations formFellingForestPlantations = new FormFellingForestPlantations();

    /**
     * Кнопка добавить
     */
    private final SelenideElement buttonAdd = $(By.xpath("//button[text() = 'Добавить']"));

    /**
     * Нажать на кнопку Добавить
     */
    public void clickButtonAdd() {
        buttonAdd.click();
    }
}
