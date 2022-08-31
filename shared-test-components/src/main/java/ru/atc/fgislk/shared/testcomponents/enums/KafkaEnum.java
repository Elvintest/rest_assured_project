package ru.atc.fgislk.shared.testcomponents.enums;

/**
 * стенды кафки, из https://kb-liga.phoenixit.ru/pages/viewpage.action?pageId=145002892
 */
public enum KafkaEnum {
    /**
     * кафка на стенде DEV
     */
    DEV("10.125.4.123:9092,10.125.4.124:9092,10.125.4.125:9092"),
    /**
     * кафка на стенде UAT
     */
    UAT("10.125.7.5:9092,10.125.7.13:9092,10.125.7.14:9092");

    /**
     * адреса узлов кластера кафки
     */
    final String bootstrapServers;

    KafkaEnum(String srv) {
        bootstrapServers = srv;
    }

    public String getBootstrapServers() {
        return bootstrapServers;
    }
}
