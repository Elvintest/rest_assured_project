package ru.atc.fgislk.ppod.testcore.lklfront.ui.pageobjects.blocks.formationdocument;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import ru.atc.fgislk.ppod.testcore.lklfront.ui.common.CalendarFgis;

import java.util.Random;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static ru.atc.fgislk.ppod.testcore.lklfront.ui.common.PagePrimitive.selectConboBox;

/**
 * Блок данные лесопользователя ФЛ
 */
public class BlockDataPersonUL {
    private final Random generator = new Random();
    /**
     * Наименование Юридического лица
     */
    private final SelenideElement orgName = $(By.xpath("//input[@name = 'org.name']"));
    /**
     * ИНН
     */
    private final SelenideElement inn = $(By.xpath("org.inn"));
    /**
     * Фамилия представителя
     */
    private final SelenideElement lastName = $(By.xpath("//input[@name = 'org.representative.lastName']"));
    /**
     * Имя представителя
     */
    private final SelenideElement firstName = $(By.xpath("//input[@name = 'org.representative.firstName']"));
    /**
     * отчество представителя
     */
    private final SelenideElement patronymic = $(By.xpath("//input[@name = 'org.representative.patronymic']"));
    /**
     * Должность представителя
     */
    private final SelenideElement post = $(By.xpath("//input[@name = 'org.representative.post']"));
    /**
     * Должность представителя
     */
    private final SelenideElement phone = $(By.xpath("//input[@name = 'org.representative.phone']"));
    /**
     * Тип документа-оснований полномочий
     */
    private final SelenideElement typeCode = $(By.xpath("//h3[text() = 'Данные представителя']/../div[2]/div[1]//input"));
    /**
     * Номер документа-основания полномочий
     */
    private final SelenideElement docNumber = $(By.xpath("//input[@name = 'org.representative.authorityDocument.docNumber']"));
    /**
     * Дата документа-оснований полномочий
     */
    private final SelenideElement dataDoc = $(By.xpath("//h3[text() = 'Данные представителя']/following-sibling::div/following-sibling::div//input[@id = 'date picker input']"));


    /**
     * Заполнить название ЮЛ
     * @param value название
     */
    public void setOrgName(String value){
        orgName.shouldBe(Condition.visible).setValue(value);
    }

    /**
     * Заполнить ИНН
     * @param value ИНН
     */
    public void setInn(String value){
        inn.shouldBe(Condition.visible).setValue(value);
    }
    /**
     * Заполнить фамилия представителя
     *
     * @param value фамилия
     */
    public void setLastName(String value) {
        lastName.shouldBe(Condition.enabled).shouldBe(Condition.visible).setValue(value);
    }

    /**
     * Заполнить имя представителя
     *
     * @param value имя
     */
    public void setFirstName(String value) {
        firstName.shouldBe(Condition.enabled).shouldBe(Condition.visible).setValue(value);
    }

    /**
     * Заполнить отчество представителя
     *
     * @param value отчество
     */
    public void setPatronymic(String value) {
        patronymic.shouldBe(Condition.enabled).shouldBe(Condition.visible).setValue(value);
    }

    /**
     * Заполнить должность представителя
     *
     * @param value должность
     */
    public void setPost(String value) {
        post.shouldBe(Condition.enabled).shouldBe(Condition.visible).setValue(value);
    }

    /**
     * Заполнить телефон представителя, 10 цифр
     *
     * @param value телефон
     */
    public void setPhone(String value) {
        phone.click();
        phone.shouldBe(Condition.enabled).shouldBe(Condition.visible).sendKeys(value);
    }

    /**
     * Выбрать тип документа-основания полномочий
     *
     * @param value тип документа-основания полномочий
     */
    public void selectTypeCode(String value) {
        ElementsCollection listTypeCode = selectConboBox(typeCode);
        listTypeCode.findBy(Condition.text(value)).click();
    }

    /**
     * Выбрать тип документа-основания полномочий
     */
    public void selectTypeCode() {
        ElementsCollection listTypeCode = selectConboBox(typeCode);
        listTypeCode.get(generator.nextInt(listTypeCode.size())).click();
    }

    /**
     * Заполнить номер документа-основания полномочий
     *
     * @param value номер
     */
    public void setDocNumber(String value) {
        docNumber.shouldBe(Condition.enabled).shouldBe(Condition.visible).setValue(value);
    }

    /**
     * Выбрать дату документа-оснований полномочий
     *
     * @param value дата в формате XX.XX.XXXX
     */
    public void setDataDoc(String value) {
        CalendarFgis calendar = new CalendarFgis(dataDoc);
        calendar.selectData(value);

    }

}
