package ru.atc.fgislk.ppod.testcore.lklfront.ui.pageobjects.blocks.downloadattachments;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import java.awt.*;
import java.net.URL;
import java.time.Duration;
import java.util.Random;

import static com.codeborne.selenide.Selenide.$;
import static ru.atc.fgislk.ppod.testcore.lklfront.ui.common.PagePrimitive.selectConboBox;
import static ru.atc.fgislk.ppod.testcore.lklfront.ui.common.PagePrimitive.uploadFileRobot;

/**
 * Форма Вложение, добавляет файл и подпись
 */
public class FormAttachment {
    Random generator = new Random();
    /**
     * Тип документа
     */
    private final SelenideElement documentType = $(By.xpath("//h3[text() = 'Вложение']/../following-sibling::main/form/div/div/div//input"));
    /**
     * Загрузить файл
     */
    private final SelenideElement uploadFile = $(By.xpath("//span[text() = 'Загрузить файл']"));
    /**
     * Загрузить файл подпись
     */
    private final SelenideElement uploadSigFile = $(By.xpath("//span[text() = 'Загрузить файл подписи']"));
    /**
     * Кнопка отмена
     */
    private final SelenideElement buttonCancel = $(By.xpath("//footer/button[text() = 'Отмена']"));
    /**
     * Кнопка Сохранить
     */
    private final SelenideElement buttonSave = $(By.xpath("//footer/button[text() = 'сохранить']"));

    /**
     * Выбрать Тип документа
     *
     * @param value название субъекта
     */
    public void selectDocumentType(String value) {
        ElementsCollection listForestry = selectConboBox(documentType);
        listForestry.findBy(Condition.text(value)).click();
    }

    /**
     * Выбрать случайный Тип документа
     */
    public void selectDocumentType() {
        ElementsCollection listForestry = selectConboBox(documentType);
        listForestry.get(generator.nextInt(listForestry.size())).click();
    }

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
