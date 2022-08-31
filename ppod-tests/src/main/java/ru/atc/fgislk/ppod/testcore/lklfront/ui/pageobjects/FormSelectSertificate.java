package ru.atc.fgislk.ppod.testcore.lklfront.ui.pageobjects;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import java.util.Random;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

/**
 * Форма выбора сертификата
 */
public class FormSelectSertificate {
    private final Random generator = new Random();
    /**
     * Список сертификатов
     */
    private final ElementsCollection listCertificate = $$(By.ById.xpath("//h2[text( ) = 'Выберите сертификат:']/parent::div//label/span[2]"));
    /**
     * Кнопка Назад
     */
    private final SelenideElement buttonPrev = $(By.xpath("//h2[text( ) = 'Выберите сертификат:']/parent::div/div[2]/button[1]"));
    /**
     * Кнопка Подписать
     */
    private final SelenideElement buttonSing = $(By.xpath("//h2[text( ) = 'Выберите сертификат:']/parent::div/div[2]/button[2]"));
    /**
     * Выбор сертификата для подписания
     */
    public void selectCertificate(String value) {
        listCertificate.find(Condition.text(value)).click();
    }

    /**
     * Выбор сертификата для подписания
     */
    public void selectCertificate() {
        listCertificate.get(generator.nextInt(listCertificate.size())).click();
    }

    /**
     * Нажать кнопку Назад
     */
    public void clickButtonPrev(){
        buttonPrev.shouldBe(Condition.enabled).click();
    }
    /**
     * Нажать кнопку Подписать
     */
    public void clickButtonSign(){
        buttonSing.shouldBe(Condition.enabled).click();
    }

}
