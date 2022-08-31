package ru.atc.fgislk.ppod.testcore.lklfront.ui.pageobjects.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

/**
 * Step - 2
 * Страница Лесохозяйственные объекты
 */
public class PageForestryObjects {
    /**
     * Кнопка добавить объект
     */
    private final SelenideElement buttonAddObject = $(By.xpath("//button[text() = 'Добавить объект']"));
    /**
     * Кнопка назад
     */
    private final SelenideElement buttonPrev = $(By.xpath("//button[text() = 'назад']"));
    /**
     * Кнопка далее
     */
    private final SelenideElement buttonNext = $(By.xpath("//button[text() = 'далее']"));

    /**
     * Нажать кнопку добавить объект
     */
    public void clickButtonAddObject(){
        buttonAddObject.shouldBe(Condition.enabled).click();
    }
    /**
     * Нажать кнопку назад
     */
    public void clickButtonPrev(){
        buttonPrev.shouldBe(Condition.enabled).click();
    }
    /**
     * Нажать кнопку далее
     */
    public void clickButtonNext(){
        buttonNext.shouldBe(Condition.enabled).click();
    }
}
