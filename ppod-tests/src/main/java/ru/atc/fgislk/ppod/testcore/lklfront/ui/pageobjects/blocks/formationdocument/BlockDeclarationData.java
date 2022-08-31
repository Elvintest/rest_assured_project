package ru.atc.fgislk.ppod.testcore.lklfront.ui.pageobjects.blocks.formationdocument;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import ru.atc.fgislk.ppod.testcore.common.RandomString;
import ru.atc.fgislk.ppod.testcore.lklfront.ui.common.CalendarFgis;

import java.time.Duration;
import java.util.List;
import java.util.Random;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static ru.atc.fgislk.ppod.testcore.lklfront.ui.common.PagePrimitive.selectConboBox;

public class BlockDeclarationData {
    private final Random generator = new Random();
    /**
     * Данные декларации, Субъект РФ
     */
    private final SelenideElement subjectRF = $(By.xpath("//h3[text() = 'Данные декларации']/../div[2]/div[1]//input"));
    /**
     * Лесничество
     */
    private final SelenideElement forestry = $(By.xpath("//h3[text() = 'Данные декларации']/../div[2]/div[2]//input"));
    /**
     * Орган государственной власти / местного самоуправления
     */
    private final SelenideElement publicAuthority = $(By.xpath("//h3[text() = 'Данные декларации']/../div[2]/div[3]//input"));
    /**
     * Номер лесной декларации
     */
    private final SelenideElement numberForestDeclaration = $(By.xpath("//input[@name = 'declarationData.declarationNumber']"));
    /**
     * Срок действия декларации
     */
    private final SelenideElement validityDeclaration = $(By.xpath("//h3[text() = 'Данные декларации']/following-sibling::div/div[5]//input[@id = 'date picker input']"));
    /**
     * Тип документа-основания аренды
     */
    private final SelenideElement typeLeaseDocument = $(By.xpath("//h3[text() = 'Данные декларации']/following-sibling::div[2]/div[1]//input"));
    /**
     * Номер документа-основания аренды
     */
    private final SelenideElement numberLeaseDocument = $(By.xpath("//input[@name = 'declarationData.contractNumber']"));

    /**
     * Дата документа-основания аренды
     */
    private final SelenideElement dataLeaseDocument = $(By.xpath("//h3[text() = 'Данные декларации']/following-sibling::div/following-sibling::div/div//input[@id = 'date picker input']"));
    /**
     * Вид использования лесов
     */
    private final SelenideElement typeForestUse = $(By.xpath("//h3[text() = 'Данные декларации']/../div[3]/div[4]//input"));


    /**
     * Выбрать субъект РФ
     *
     * @param value название субъекта
     */
    public void selectSubjectRF(String value) {
        ElementsCollection listSubjectRF = selectConboBox(subjectRF);
        listSubjectRF.findBy(Condition.text(value)).click();
    }

    /**
     * Выбрать субъект РФ
     */
    public void selectSubjectRF() {
        ElementsCollection listSubjectRF = selectConboBox(subjectRF);
        listSubjectRF.get(generator.nextInt(listSubjectRF.size())).click();
    }

    /**
     * Выбрать лесничество
     *
     * @param value название субъекта
     */
    public void selectForestry(String value) {
        forestry.find(By.xpath(".//preceding-sibling::div")).shouldBe(Condition.enabled).click();
        ElementsCollection listForestry = $$(By.xpath("//div[@id = 'menu-declarationData.forestry']//li")).shouldBe(CollectionCondition.sizeNotEqual(0), Duration.ofSeconds(2));
        listForestry.findBy(Condition.text(value)).click();
    }

    /**
     * Выбрать лесничество
     */
    public void selectForestry() {
        forestry.find(By.xpath(".//preceding-sibling::div")).shouldBe(Condition.enabled).click();
        ElementsCollection listForestry = $$(By.xpath("//div[@id = 'menu-declarationData.forestry']//li")).shouldBe(CollectionCondition.sizeNotEqual(0), Duration.ofSeconds(2));
        listForestry.get(generator.nextInt(listForestry.size())).click();
    }

    /**
     * Выбрать Орган государственной власти / местного самоуправления
     *
     * @param value название
     */
    public void selectPublicAuthority(String value) {
        ElementsCollection listPublicAuthority = selectConboBox(publicAuthority);
        listPublicAuthority.findBy(Condition.text(value)).click();
    }

    /**
     * Выбрать Орган государственной власти / местного самоуправления
     */
    public void selectPublicAuthority() {
        ElementsCollection listPublicAuthority = selectConboBox(publicAuthority);
        listPublicAuthority.get(generator.nextInt(listPublicAuthority.size())).click();
    }

    /**
     * Заполнить номер лесной декларации
     *
     * @param value номер декларации
     */
    public void setNumberForestDeclaration(String value) {
        numberForestDeclaration.shouldBe(Condition.enabled).setValue(value);
    }

    /**
     * Установить срок действия декларации
     *
     * @param startData дата начала
     * @param endData   дата окончания
     */
    public void setDataValidityDeclaration(String startData, String endData) {
        CalendarFgis calendar = new CalendarFgis(validityDeclaration);
        calendar.selectData(startData, endData);
    }

    /**
     * Выбрать тип документа-основания аренды
     *
     * @param value название документа-основания аренды
     */
    public void setTypeLeaseDocument(String value) {
        ElementsCollection listTypeLeaseDocument = selectConboBox(typeLeaseDocument);
        listTypeLeaseDocument.findBy(Condition.text(value)).click();
    }

    /**
     * Выбрать Тип документа-основания аренды
     */
    public void setTypeLeaseDocument() {
        ElementsCollection listTypeLeaseDocument = selectConboBox(typeLeaseDocument);
        listTypeLeaseDocument.get(generator.nextInt(listTypeLeaseDocument.size())).click();
    }

    /**
     * Заполнмть номер документа- основания аренды
     *
     * @param value номер
     */
    public void setNumberLeaseDocument(String value) {
        numberLeaseDocument.shouldBe(Condition.enabled).setValue(value);
    }

    /**
     * Установить дату документа-основания аренды
     *
     * @param value дата документа
     */
    public void setDataLeaseDocument(String value) {
        CalendarFgis calendar = new CalendarFgis(dataLeaseDocument);
        calendar.selectData(value);
    }

    /**
     * Выбрать вид использования лесов
     * @param values список значений
     */
    public void setTypeForestUse(List<String> values) {
        String id = typeForestUse.getAttribute("id");
        for (String value : values) {
            typeForestUse.setValue(value);
            $$(By.xpath("//ul[@id = '" + id + "-listbox']/li")).should(CollectionCondition.sizeNotEqual(0), Duration.ofSeconds(5)).findBy(Condition.text(value)).click();

        }
    }

    /**
     * Выбрать вид использования лесов
     * @param value количество позиций для выбора
     */
    public void setTypeForestUse(int value) {
        String id = typeForestUse.getAttribute("id");
        typeForestUse.find(By.xpath("./parent::div//button")).shouldBe(Condition.visible).click();
        ElementsCollection listTypeForestUse = $$(By.xpath("//ul[@id = '" + id + "-listbox']/li")).should(CollectionCondition.sizeNotEqual(0), Duration.ofSeconds(5));
        for (int i = 0; i < value; i++) {
            String text = listTypeForestUse.get(generator.nextInt(listTypeForestUse.size())).getText();
            typeForestUse.setValue(text);
            $$(By.xpath("//ul[@id = '" + id + "-listbox']/li"))
                    .should(CollectionCondition.sizeNotEqual(0), Duration.ofSeconds(5))
                    .findBy(Condition.text(text))
                    .click();
        }
    }



}
