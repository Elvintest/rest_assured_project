package ru.atc.fgislk.ppod.testcore.lklfront.ui.pageobjects.blocks.addobject;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

/**
 * Форма добавления координат объекта
 */
public class FormAddCoordinates {
    /**
     * Широта
     */
    private final SelenideElement latitude = $(By.xpath("//input[@name = 'latitude']"));
    /**
     * Долгота
     */
    private final SelenideElement longitude = $(By.xpath("//input[@name = 'longitude']"));
    /**
     * Кнопка сохранить
     */
    private final SelenideElement buttonSave = $(By.xpath("//footer/button[text() = 'сохранить']"));
    /**
     * Кнопка отмена
     */
    private final SelenideElement buttonCancel = $(By.xpath("//footer/button[text() = 'Отмена']"));


    /**
     * Ввести широту
     * @param value широта
     */
    public void setLatitude(String value){
        latitude.setValue(value);
    }

    /**
     * Ввести долготу
     * @param value долгота
     */
    public void setLongitude(String value){
        longitude.setValue(value);
    }

    /**
     * Нажать на кнопку Сохранить
     */
    public void clickButtonSave() {
        buttonSave.click();
    }
    /**
     * Нажать на кнопку Отмена
     */
    public void clickButtonCancel() {
        buttonCancel.click();
    }
}
