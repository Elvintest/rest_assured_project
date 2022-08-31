package ru.atc.fgislk.ppod.testcore.lklfront.ui.common;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CalendarFgis {
    /**
     * Элемент ввода календаря
     */
    private SelenideElement calendar;
    /**
     * Основной блок календаря
     */
    private SelenideElement blockCalendar;
    /**
     * Блок  месяц год
     */
    private SelenideElement blockDay;
    /**
     * Нажатие на месяц год
     */
    private SelenideElement clickMountYear;
    /**
     * Блок  месяц
     */
    private SelenideElement blockMounth;
    /**
     * Нажатие на год
     */
    private SelenideElement clickYear;
    /**
     * Блок год
     */
    private SelenideElement blockYar;

    /**
     * Конструктор по элементу
     *
     * @param calendar Элемент календарь
     */
    public CalendarFgis(SelenideElement calendar) {
        this.calendar = calendar;
    }


    /**
     * Выбрать в календаре дату
     *
     * @param data дата в формате dd.mm.yyyy
     */
    private void setData(String data) {
        String[] splitData = data.split("\\.");

        selectYear(splitData[2]);
        selectMonth(splitData[1]);
        selectDay(splitData[0]);
    }

    /**
     * ВЫбрать диапазон дат
     *
     * @param startData дата начала в формате dd.mm.yyyy
     * @param endData дата окончания в формате dd.mm.yyyy
     */
    public void selectData(String startData, String endData) {
        selectData(startData);
        clickMountYear.click();
        clickYear.click();
        setData(endData);
    }

    /**
     * Выбрать дату в календаре
     * @param data дата в формате dd.mm.yyyy
     */
    public void selectData(String data) {
        calendar.scrollTo().click();
        blockCalendar = calendar.find(By.xpath("./../../../../../.."));
        blockDay = blockCalendar.find(By.xpath("(.//div[@class = 'react-datepicker__month-container'])[1]"));
        clickMountYear = blockDay.find(By.xpath(".//input"));
        clickMountYear.click();
        blockMounth = blockCalendar.find(By.xpath("(.//div[@class = 'react-datepicker__month-container'])[2]"));
        clickYear = blockMounth.find(By.xpath(".//input"));
        blockYar = blockCalendar.find(By.xpath(".//div[@class = 'react-datepicker__year--container']"));
        clickYear.click();
        setData(data);
    }


    /**
     * Рекурсивная функция выбора года
     *
     * @param year год
     */
    private void selectYear(String year) {
        SelenideElement buttonPrev = blockYar.find(By.xpath(".//button[1]"));
        SelenideElement buttonNext = blockYar.find(By.xpath(".//button[2]"));

        ElementsCollection years = blockYar.findAll(By.xpath(".//div[contains(@class, 'react-datepicker__year-text')]"));

        if (!years.filter(Condition.text(year)).isEmpty()) {
            years.filter(Condition.text(year)).first().click();
            return;
        }

        int minYear = Integer.parseInt(Objects.requireNonNull(years.first().text()));

        if (Integer.parseInt(year) < minYear)
            buttonPrev.click();
        else
            buttonNext.click();
        selectYear(year);
    }

    /**
     * Выбрать месяц
     * @param mounth номер месяца
     */
    private void selectMonth(String mounth) {
        Map<String, String> map = new HashMap<>();
        map.put("01", "янв.");
        map.put("02", "фев.");
        map.put("03", "март");
        map.put("04", "апр.");
        map.put("05", "май");
        map.put("06", "июнь");
        map.put("07", "июль");
        map.put("08", "авг.");
        map.put("09", "сент.");
        map.put("10", "окт.");
        map.put("11", "нояб.");
        map.put("12", "дек.");

        ElementsCollection mounths = blockMounth
                .findAll(By.xpath(".//div[contains(@class, 'react-datepicker__month-text react-datepicker__month-')]"))
                .filter(Condition.text(map.get(mounth)));

        mounths.first().click();
    }

    /**
     * Выбрать день
     * @param day день
     */
    private void selectDay(String day) {
        blockDay.findAll(By.xpath(".//div[contains(@class, 'react-datepicker__day--') and not(contains(@class, 'react-datepicker__day--outside-month'))]"))
                .filter(Condition.text(String.valueOf(Integer.parseInt(day)))).first().click();
    }
}
