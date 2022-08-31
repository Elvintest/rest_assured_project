package ru.atc.fgislk.ppod.testcore.lklfront.ui.pageobjects.blocks.formationdocument;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import java.util.Random;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static ru.atc.fgislk.ppod.testcore.lklfront.ui.common.PagePrimitive.selectConboBox;

/**
 * Блок данные лесопользователя ФЛ
 */
public class BlockDataPersonFL {
    private final Random generator = new Random();
    /**
     * Фамилия
     */
    private final SelenideElement personLastName = $(By.xpath("//input[@name = 'person.lastName']"));
    /**
     * Имя
     */
    private final SelenideElement personFirstName = $(By.xpath("//input[@name = 'person.firstName']"));
    /**
     * Отчество
     */
    public final SelenideElement personPatronymic = $(By.xpath("//input[@name = 'person.patronymic']"));

    /**
     * Документ удостоверяющий личность
     */
    private final SelenideElement personDocumentCode = $(By.xpath("//h3[text() = 'Данные лесопользователя']/../div[2]/div[4]//input"));
    /**
     * Серия документа
     */
    private final SelenideElement personDocumentSeries = $(By.xpath("//input[@name = 'person.documentSeries']"));
    /**
     * Номер документа
     */
    private final SelenideElement personDocumentNumber = $(By.xpath("//input[@name = 'person.documentNumber']"));

    /**
     * Заполнить поле Фамилия
     *
     * @param value фамилия
     */
    public void setPersonLastName(String value) {
        personLastName.shouldBe(Condition.enabled).setValue(value);
    }

    /**
     * Заполнить Имя
     *
     * @param value имя
     */
    public void setPersonFirstName(String value) {
        personFirstName.shouldBe(Condition.enabled).setValue(value);
    }

    /**
     * Заполнить Отчество
     *
     * @param value отчество
     */
    public void setPersonPatronymic(String value) {
        personPatronymic.shouldBe(Condition.enabled).setValue(value);
    }

    /**
     * Выбрать документ удостоверяющий личность
     *
     * @param value название документа
     */
    public void selectPersonDocumentCode(String value) {
        ElementsCollection listMenuPersonDocumentCode = selectConboBox(personDocumentCode);
        listMenuPersonDocumentCode.findBy(Condition.text(value)).click();
    }

    /**
     * Выбрать случайный документ удостоверяющий личглсть
     */
    public void selectPersonDocumentCode() {
        ElementsCollection listMenuPersonDocumentCode = selectConboBox(personDocumentCode);
        listMenuPersonDocumentCode.get(generator.nextInt(listMenuPersonDocumentCode.size())).click();
    }

    /**
     * Заполнить серию документа
     */
    public void setPersonDocumentSeries(String value) {
        personDocumentSeries.shouldBe(Condition.enabled).setValue(value);
    }

    /**
     * Заполнить номер документа
     */
    public void setPersonDocumentNumber(String value) {
        personDocumentNumber.shouldBe(Condition.enabled).setValue(value);
    }
}
