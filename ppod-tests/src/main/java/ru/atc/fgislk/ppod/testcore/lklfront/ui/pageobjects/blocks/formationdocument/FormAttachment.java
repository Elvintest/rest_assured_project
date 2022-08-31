package ru.atc.fgislk.ppod.testcore.lklfront.ui.pageobjects.blocks.formationdocument;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import java.awt.*;
import java.net.URL;
import java.time.Duration;
import java.util.Random;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static java.lang.Thread.sleep;
import static ru.atc.fgislk.ppod.testcore.lklfront.ui.common.PagePrimitive.uploadFileRobot;

/**
 * Форма Вложение, добавляет файл и подпись
 */
public class FormAttachment {
    Random generator = new Random();
    /**
     * Загрузить файл
     */
    private final SelenideElement uploadFile = $(By.xpath("//span[text() = 'Загрузить файл']"));
    /**
     * Загрузить файл подпись
     */
    private final SelenideElement uploadSigFile = $(By.xpath("//span[text() = 'Загрузить файл подписи']"));
    /**
     * Масштаб
     */
    private final SelenideElement scale = $(By.xpath("//input[@name = 'scale']"));
    /**
     * Кнопка загрузить документ
     */
    private final SelenideElement buttonUploadDoc = $(By.xpath("//button[text() = 'загрузить документ']"));
    /**
     * Кнопка отмена
     */
    private final SelenideElement buttonCancel = $(By.xpath("//button[text() = 'Отмена']"));
    /**
     * Кнопка Сохранить
     */
    private final SelenideElement buttonSave = $(By.xpath("//div[@role = 'dialog']//button[text() = 'сохранить']"));

    /**
     * Загрузить файл
     *
     * @param value пут ь к файлу
     */
    public void addFile(String value) throws AWTException {
        ClassLoader classLoader = getClass().getClassLoader();
        URL res = classLoader.getResource(value);
        assert res != null;
        uploadFile.click();
        uploadFileRobot(res.getPath());
        uploadFile.shouldNotBe(Condition.exist, Duration.ofSeconds(5));
    }

    /**
     * Загрузить файл подписи
     *
     * @param value путь к файлу
     */
    public void addSigFile(String value) throws AWTException {
        ClassLoader classLoader = getClass().getClassLoader();
        URL res = classLoader.getResource(value);
        assert res != null;
        uploadSigFile.click();
        uploadFileRobot(res.getPath());
        uploadSigFile.shouldNotBe(Condition.exist, Duration.ofSeconds(5));
    }

    /**
     * Установить масштаб
     *
     * @param value масштаб
     */
    public void setScale(String value) {
        scale.shouldBe(Condition.visible).setValue(value);
    }

    /**
     * Нажать на кнопку загрузить документ
     */
    public void clickButtonUploadDoc() {
        buttonUploadDoc.shouldBe(Condition.enabled).click();
    }

    /**
     * Выбрать случайный файл из списка
     */
    public void selectFileUpload() throws InterruptedException {
        sleep(1000);
        ElementsCollection listUploadFile = $$(By.xpath("//span[@title = 'Toggle Row Selected']/input[@type = 'radio']")).shouldBe(CollectionCondition.sizeNotEqual(0), Duration.ofSeconds(5));
        listUploadFile.get(generator.nextInt(listUploadFile.size())).click();
    }

    /**
     * Выбрать файл в списке по названию
     *
     * @param value название файла
     */
    public void selectFileUpload(String value) {
        $(By.xpath("//td[text() = '" + value + "']/..//input")).click();
    }

    /**
     * Нажать на кнопку Отмена
     */
    public void clickButtonCancel() {
        buttonCancel.click();
    }

    /**
     * Нажать на кнопку Сохранить
     */
    public void clickButtonSave() {
        buttonSave.shouldBe(Condition.enabled).click();
    }

}
