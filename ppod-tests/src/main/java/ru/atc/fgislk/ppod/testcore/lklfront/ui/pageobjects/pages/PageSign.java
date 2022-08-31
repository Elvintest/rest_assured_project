package ru.atc.fgislk.ppod.testcore.lklfront.ui.pageobjects.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import ru.atc.fgislk.ppod.testcore.lklfront.ui.pageobjects.FormSelectSertificate;

import static com.codeborne.selenide.Selenide.$;

/**
 * Step - 4
 * Старница подписания сформированных документов Step 4
 */

public class PageSign {
    public FormSelectSertificate formSelectSertificate = new FormSelectSertificate();
    /**
     * Кнопка На главную
     */
    private final SelenideElement buttonHome = $(By.xpath("//button[text() = 'на главную']"));
    /**
     * Кнопка Подписать
     */
    private final SelenideElement buttonSign = $(By.xpath("//button[text() = 'подписать']"));


    /**
     * Нажать на кнопку На главную
     */
    public void clickButtonHome() {
        buttonHome.shouldBe(Condition.enabled).click();
    }

    /**
     * Нажать на кнопку Подписать
     */
    public void clickButtonSign() {
        buttonSign.shouldBe(Condition.enabled).click();
    }

}
