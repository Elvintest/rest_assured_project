package ru.atc.fgislk.ppod.testcore.common;

import org.apache.commons.lang3.RandomStringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class RandomString {

    /**
     * Герерация случайной строки
     * @param length - количество символов
     * @return случайная строка
     */
    public static String randomString(Integer length) {
        return RandomStringUtils.random(length, true, false).toLowerCase();
    }

    /**
     * Генерация случайной строки из симвоов и чисел
     * @param length - длинна строки
     * @return случайная строка
     */
    public static String randomCharInt(Integer length) {
        return RandomStringUtils.randomAlphanumeric(length);
    }

    /**
     * Генерация случайного GUID
     * @return GUID
     */
    public static String randomGUID(){
        return UUID.randomUUID().toString();
    }

    /**
     * Генерация случайного email
     * @return email
     */
    public static String randomEmail() {
        return randomString(10) + "@" + randomString(5) + ".ru";
    }

    /**
     * Генерация случайной строки из чисел
     * @param length - длинна строки
     * @return случайная строка
     */
    public static String randomInt(Integer length) {
        return RandomStringUtils.random(length, '1', '2', '3', '4', '5','6', '7', '8', '9'); // убрал 0
    }

    /**
     * Генерация случайной строки из чисел в диапазоне
     * @param min - начальное число
     * @param max - конечное значение
     * @return случайная строка
     */
    public static String randomIntRange(Integer min, Integer max) {
        long res = min + (int) (Math.random() * ((max - min) + 1));
        return String.valueOf(res);
    }

    /**
     * Возвращает случайную дату в диапазоне (даты в формате 01.01.2020)<br>
     * Шаблон даты по умолчанию dd.MM.yyyy
     * @param from начальная дата
     * @param to конечная дата
     * @return Случайная дата
     */
    public static String randomDateRange(String from, String to) throws ParseException {
        return randomDateRange(from, to, "dd.MM.yyyy");
    }

    /**
     * Возвращает случайную дату в диапазоне (даты в формате 01.01.2020)
     * @param from начальная дата
     * @param to конечная дата
     * @param template шаблон получаемой даты
     * @return Случайная дата
     */
    public static String randomDateRange(String from, String to, String template) throws ParseException {
        SimpleDateFormat formatDate = new SimpleDateFormat(template);
        Date fromDate = formatDate.parse(from);
        Date toDate = formatDate.parse(to);
        Date randomDate = new Date(ThreadLocalRandom.current().nextLong(fromDate.getTime(), toDate.getTime()));

        return new SimpleDateFormat(template).format(randomDate.getTime());
    }

}
