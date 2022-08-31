package ru.atc.fgislk.ppod.testcore.lklfront.services;


import ru.atc.fgislk.ppod.testcore.lklfront.services.CamundaStep;
import ru.atc.fgislk.ppod.testcore.lklfront.services.ConnectDB;
import ru.atc.fgislk.ppod.testcore.lklfront.services.LklFront;
import ru.atc.fgislk.shared.testcomponents.enums.StendsDescriptionEnum;

public class AppConfig {
    /**
     * Работа с API, общие методы
     */
    public static LklFront lklFront;
    /**
     * Работа с камундой
     */
    public static CamundaStep camundaService;
    /**
     * Работа с БД
     */
    public static ConnectDB dataBase;
    /**
     * Стенд
     */
    public static StendsDescriptionEnum stend;
    /**
     * Сертификат для подписи
     */
    public static String certificate = "*.112.2o7.net, DO_NOT_TRUST_FiddlerRoot, действительно с 29.06.2022 по 29.06.2023";

}
