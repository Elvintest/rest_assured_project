package ru.atc.fgislk.ppod.testcore.lklfront.ui.pageobjects.pages;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import ru.atc.fgislk.ppod.testcore.lklfront.ui.pageobjects.blocks.downloadattachments.FormAttachment;

import static com.codeborne.selenide.Selenide.$;

/**
 * Step 3
 * Страница Загрузка вложений
 */
public class PageDownloadAttachments {
    /**
     * Форма добавления фложения
     */
    public FormAttachment formAttachment = new FormAttachment();

    /**
     * Кнопка Загрузить
     */
    private final SelenideElement buttonUpload = $(By.xpath("//button[text() = 'Загрузить']"));
    /**
     * Кнопка Назад
     */
    private final SelenideElement buttonBack = $(By.xpath("//button[text() = 'назад']"));
    /**
     * Кнопка Сформировать документ
     */
    private final SelenideElement buttonGenerateDoc = $(By.xpath("//button[text() = 'сформировать документ']"));

    /**
     * Нажать на кнопку Загрузить
     */
    public void clickButtonUpload() {
        buttonUpload.click();
    }

    /**
     * Нажать на кнопку Назад
     */
    public void clickButtonBack() {
        buttonBack.click();
    }

    /**
     * Нажать на кнопку Сформировать документ
     */
    public void clickButtonGenerateDoc() {
        buttonGenerateDoc.click();
    }
}
