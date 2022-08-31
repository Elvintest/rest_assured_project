package ru.atc.fgislk.ppod.testcore.lklfront.ui.pageobjects.blocks.formationdocument;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import java.util.Random;

import static com.codeborne.selenide.Selenide.$;

/**
 * Блок данные лесопользователя ИП
 */
public class BlockDataPersonIP {
    private final Random generator = new Random();
    /**
     * Фамилия
     */
    private final SelenideElement lastName = $(By.xpath("//input[@name = 'ip.lastName']"));
    /**
     * Имя
     */
    private final SelenideElement firstName = $(By.xpath("//input[@name = 'ip.firstName']"));
    /**
     * Отчество
     */
    private final SelenideElement patronymic = $(By.xpath("//input[@name = 'ip.patronymic']"));


    /**
     * Заполнить фамилию
     *
     * @param value фамилия
     */
    public void setLastName(String value) {
        lastName.shouldBe(Condition.visible).setValue(value);
    }

    /**
     * Заполнить имя
     *
     * @param value имя
     */
    public void setFirstName(String value) {
        firstName.shouldBe(Condition.visible).setValue(value);
    }

    /**
     * Заполнить отчество
     *
     * @param value отчество
     */
    public void setPatronymic(String value) {
        patronymic.shouldBe(Condition.visible).setValue(value);
    }
}
