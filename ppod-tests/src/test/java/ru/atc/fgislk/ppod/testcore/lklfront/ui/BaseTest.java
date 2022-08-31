package ru.atc.fgislk.ppod.testcore.lklfront.ui;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import ru.atc.fgislk.ppod.testcore.lklfront.ui.common.Driver;

public class BaseTest {
    @BeforeMethod
    public void setUp() {
        Driver.initDriver();
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());

    }

    @AfterMethod
    public void tearDown() {
//        Driver.clearCookies();
        Driver.close();
    }
}
