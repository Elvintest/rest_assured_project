package ru.atc.fgislk.ppod.testcore.lklfront.ui.common;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.time.Duration;

import static com.codeborne.selenide.Selenide.$$;

public class PagePrimitive {
    private PagePrimitive() {
        throw new IllegalStateException("PagePrimitive");
    }

    /**
     * Загрузка файла на страницу с вызовом формы выбора файла
     *
     * @param value путь к файлу
     */
    public static void uploadFileRobot(String value) throws AWTException {
        value = value.substring(1, value.length()).replaceAll("/", "\\\\");
        StringSelection stringSelection = new StringSelection(value);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);

        Robot robot = new Robot();
        robot.delay(1000);
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.delay(200);
        robot.keyRelease(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.delay(200);
        robot.keyRelease(KeyEvent.VK_ENTER);
    }

    /**
     * Раскрывает комбобокс и получает список значений
     *
     * @param el combobox
     * @return список значений
     */
    public static ElementsCollection selectConboBox(SelenideElement el) {
        String id = el.getAttribute("id");
        SelenideElement buttonDropDown = el.find(By.xpath("./parent::div//button"));

        buttonDropDown.shouldBe(Condition.visible).click();
        return $$(By.xpath("//ul[@id = '" + id + "-listbox']/li")).should(CollectionCondition.sizeNotEqual(0), Duration.ofSeconds(5));
    }

    /**
     * Раскрывает комбобокс и получает список значений
     *
     * @param el    combobox
     * @param value id списка значений
     * @return список значений
     */
    public static ElementsCollection selectConboBox(SelenideElement el, String value) {
        el.find(By.xpath(".//preceding-sibling::div")).shouldBe(Condition.enabled).click();
        return $$(By.xpath("//div[@id = '" + value + "']//li")).shouldBe(CollectionCondition.sizeNotEqual(0), Duration.ofSeconds(4));
    }

}
