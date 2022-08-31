package ru.atc.fgislk.ppod.testcore.lklfront.ui.common;

public class TestConfig {
    private TestConfig() {
        throw new IllegalStateException("TestConfig class");
    }
    //Тесты запускаются командой mvn test -Dbrowser=chrome -Dheadless=1

    public static String browser = "chrome";
    public static String headless = "0";

    public static void initConfig() {
        browser = System.getProperty("browser") == null ? "chrome" : System.getProperty("browser");
        headless = System.getProperty("headless") == null ? "0" : System.getProperty("headless");
    }

    public static boolean isHeadless() {
        return headless.contains("1");
    }
}
