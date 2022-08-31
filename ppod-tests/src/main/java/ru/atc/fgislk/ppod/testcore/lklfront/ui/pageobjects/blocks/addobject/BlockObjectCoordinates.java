package ru.atc.fgislk.ppod.testcore.lklfront.ui.pageobjects.blocks.addobject;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import java.nio.channels.SelectableChannel;

import static com.codeborne.selenide.Selenide.$;

/**
 * Блок Координаты объекта
 */
public class BlockObjectCoordinates {
    /**
     * Форма добавление координат
     */
    public final FormAddCoordinates formAddCoordinates = new FormAddCoordinates();
    /**
     * Кнопка Добавить координаты
     */
    private final SelenideElement addCoordinates = $(By.xpath("//button[text() = 'Добавить координаты']"));

    /**
     * Нажать на кнопку Добавить координаты
     */
    public void clickAddCoordinates(){
        addCoordinates.shouldBe(Condition.enabled, Condition.visible).click();
    }
}
