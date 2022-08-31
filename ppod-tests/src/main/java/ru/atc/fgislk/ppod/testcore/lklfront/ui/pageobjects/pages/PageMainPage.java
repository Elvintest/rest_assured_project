package ru.atc.fgislk.ppod.testcore.lklfront.ui.pageobjects.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import ru.atc.fgislk.ppod.testcore.lklfront.ui.common.Driver;
import ru.atc.fgislk.ppod.testcore.lklfront.ui.common.Trim;

import static com.codeborne.selenide.Condition.visible;
import static ru.atc.fgislk.ppod.testcore.lklfront.ui.common.Driver.maximize;
import static com.codeborne.selenide.Selenide.*;

public class PageMainPage {
    protected String baseUrl;

    public PageMainPage(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Step("Открытие главной страницы")
    public void open() {
        String url = Trim.rtrim(baseUrl, "/") + "/" + Trim.ltrim("/", "/");
        Driver.open(url);
        maximize();
    }

    /**
     * Выбор процесса в блоке
     * @param nameBlock Блок процесса
     * @param nameProcess Процесс
     */
    @Step("Выбираем в блоке {nameBlock} {nameProcess}")
    public void selectProcess(String nameBlock, String nameProcess) {
        // Найдем нужный блок
        SelenideElement block = $(By.xpath("//button/p[text() = '" + nameBlock + "']"));
        block.shouldBe(Condition.visible).click();
        // Нажмем на название процесса в блоке
        block.find(By.xpath("../following-sibling::div//a/h4[text() = '" + nameProcess + "']")).shouldBe(visible).click();
    }
//h3[text() = 'Данные лесопользователя']/parent::div
}
