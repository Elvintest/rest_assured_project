package ru.atc.fgislk.ppod.testcore.lklfront.ui.pageobjects.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import ru.atc.fgislk.ppod.testcore.lklfront.ui.pageobjects.blocks.formationdocument.*;

import java.util.Random;

import static com.codeborne.selenide.Selenide.*;

/**
 * Step - 1
 * Страница Формирование документа «Лесная декларация»
 */
public class PageFormationDocument {
    private final Random generator = new Random();

    /**
     * Блок данные пользователя ФЛ
     */
    public BlockDataPersonFL blockDataPersonFL = new BlockDataPersonFL();
    /**
     * Блок данные пользователя ЮЛ
     */
    public BlockDataPersonUL blockDataPersonUL = new BlockDataPersonUL();
    /**
     * Блок данные пользователя ИП
     */
    public BlockDataPersonIP blockDataPersonIP = new BlockDataPersonIP();
    /**
     * Декларация подается представителем ФЛ
     */
    public BlockRepresentativeFL blockRepresentativeFL = new BlockRepresentativeFL();
    /**
     * Декларация подается представителем ИП
     */
    public BlockRepresentativeIP blockRepresentativeIP = new BlockRepresentativeIP();
    /**
     * Данные декларации
     */
    public BlockDeclarationData blockDeclarationData = new BlockDeclarationData();

    /**
     * Кнопка далее
     */
    private final SelenideElement buttonNext = $(By.xpath("//button[text() = 'далее']"));

    /**
     * Выбор тип пользователя
     *
     * @param value тип пользователя
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
     * Нажать кнопку ДАЛЕЕ
     */
    public void clickButtonNext(){
        buttonNext.shouldBe(Condition.enabled).click();
    }

}
