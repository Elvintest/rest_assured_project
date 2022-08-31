package ru.atc.fgislk.ppod.testcore.lklfront.ui.pageobjects.pages;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import ru.atc.fgislk.ppod.testcore.lklfront.ui.common.CalendarFgis;

import java.time.Duration;
import java.util.Random;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static ru.atc.fgislk.ppod.testcore.lklfront.ui.common.PagePrimitive.selectConboBox;

/**
 * Step - 5
 * Страница отправки документов
 */
public class PageSendDocuments {
    private final Random generator = new Random();
    /**
     * Фамилия
     */
    private final SelenideElement lastName = $(By.name("person.lastName"));
    /**
     * Имя
     */
    private final SelenideElement firstName = $(By.name("person.firstName"));
    /**
     * Отчество
     */
    private final SelenideElement patronymic = $(By.name("person.patronymic"));
    /**
     * Адреы ФИАС
     */
    private final SelenideElement fias = $(By.xpath("//form/div/div[2]/div[4]/div/div/div"));
    /**
     * Тип документа, удостоверяющий личность
     */
    private final SelenideElement documentType = $(By.xpath("//h3[text() = 'Данные отправителя']/parent::div/div[2]/div[5]//input"));
    /**
     * Серия документа, удостоверяющего личность
     */
    private final SelenideElement series = $(By.name("person.identityDocument.series"));
    /**
     * Номер документа
     */
    private final SelenideElement number = $(By.name("person.identityDocument.number"));
    /**
     * Орган, выдавший документ
     */
    private final SelenideElement issueName = $(By.name("person.identityDocument.issueName"));
    /**
     * Дата выдачи документа
     */
    private final SelenideElement issueData = $(By.id("date picker input"));
    /**
     * Код подразделения
     */
    private final SelenideElement issueCode = $(By.name("person.identityDocument.issueCode"));
    /**
     * СНИЛС
     */
    private final SelenideElement snils = $(By.name("person.snils"));
    /**
     * ИНН
     */
    private final SelenideElement inn = $(By.name("person.inn"));
    /**
     * email
     */
    private final SelenideElement email = $(By.name("person.email"));
    /**
     * Кнопки На главную
     */
    private final SelenideElement buttonHome = $(By.id("//button[text() = 'на главную']"));
    /**
     * Кнопки На главную
     */
    private final SelenideElement buttonSend = $(By.id("//button[text() = 'отправить']"));

    /**
     * Выбор отправителя
     *
     * @param value отправитель
     */
    public void selectUser(String value) {
        switch (value) {
            case "Физическое лицо":
                $(By.xpath("//input[@value = 'person']")).click();
                break;
            case "Юридическое лицо":
                $(By.xpath("//input[@value = 'org']")).click();
                break;
            case "ip":
                $(By.xpath("//input[@value = 'person']")).click();
                break;
            default:
                break;
        }
    }

    /**
     * Заполнить фамилию
     *
     * @param value фамилия
     */
    public void setLastName(String value) {
        lastName.shouldBe(visible).setValue(value);
    }

    /**
     * Заполнить имя
     *
     * @param value имя
     */
    public void setFirstName(String value) {
        firstName.shouldBe(visible).setValue(value);
    }

    /**
     * Заполнить отчество
     *
     * @param value отчество
     */
    public void setPatronymic(String value) {
        patronymic.shouldBe(visible).setValue(value);
    }

    /**
     * Ввести адрес ФИАС
     *
     * @param value адрес
     */
    public void setFias(String value) {
        fias.find(".//input").setValue(value);
        ElementsCollection listAdres = fias.findAll(".//li");
        listAdres.find(text(value)).click();
    }

    /**
     * Ввести адрес ФИАС
     */
    public void setFias() {
        fias.shouldBe(visible, Duration.ofSeconds(5)).find(By.xpath(".//div/input")).setValue("Кур");
        ElementsCollection listAdres = fias.findAll(".//li").shouldBe(CollectionCondition.sizeNotEqual(0), Duration.ofSeconds(5));
        listAdres.get(generator.nextInt(listAdres.size())).click();
    }

    /**
     * Выбрать Тип документа, удостоверяющий личность
     *
     * @param value название
     */
    public void selectDocumentType(String value) {
        ElementsCollection listDocumentType = selectConboBox(documentType);
        listDocumentType.findBy(text(value)).click();
    }

    /**
     * Выбрать Тип документа, удостоверяющий личность
     */
    public void selectDocumentType() {
        ElementsCollection listDocumentType = selectConboBox(documentType);
        listDocumentType.get(generator.nextInt(listDocumentType.size())).click();
    }

    /**
     * Ввести Серия документа, удостоверяющего личность
     *
     * @param value серия документа
     */
    public void setSeries(String value) {
        series.shouldBe(visible).setValue(value);
    }

    /**
     * Ввести Номер документа
     *
     * @param value номер документа
     */
    public void setNumber(String value) {
        number.shouldBe(visible).setValue(value);
    }

    /**
     * Ввести Орган, выдавший документ
     *
     * @param value Орган, выдавший документ
     */
    public void setIssueName(String value) {
        issueName.shouldBe(visible).setValue(value);
    }

    /**
     * Выбрать Дата выдачи документа
     *
     * @param value Дата выдачи документа
     */
    public void setIssueData(String value) {
        CalendarFgis calendar = new CalendarFgis(issueData);
        calendar.selectData(value);

    }

    /**
     * Ввести Код подразделения
     *
     * @param value Код подразделения
     */
    public void setIssueCode(String value) {
        issueCode.shouldBe(visible).setValue(value);
    }

    /**
     * Ввсести СНИЛС
     *
     * @param value СНИЛС
     */
    public void setSnils(String value) {
        snils.shouldBe(visible).setValue(value);
    }

    /**
     * Ввести ИНН
     *
     * @param value ИНН
     */
    public void setInn(String value) {
        inn.shouldBe(visible).setValue(value);
    }

    /**
     * Ввести email
     *
     * @param value email
     */
    public void setEmail(String value) {
        email.shouldBe(visible).setValue(value);
    }

    /**
     * Нажать на кнопку На главную
     */
    public void clickButtonHome() {
        buttonHome.shouldBe(enabled).click();
    }

    /**
     * Нажать на кнопку Отправить
     */
    public void clickButtonSend() {
        buttonSend.shouldBe(enabled).click();
    }
}
