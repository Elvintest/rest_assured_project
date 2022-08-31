package ru.atc.fgislk.ppod.testcore.lklfront.ui.common;

import java.util.regex.Pattern;

public class Trim {
    private Trim() {
        throw new IllegalStateException("Trim class");
    }

    public static String trim(String string, String trimSymbol) {
        trimSymbol = Pattern.quote(trimSymbol);
        String trimmed = ltrim(string, trimSymbol);
        return rtrim(trimmed, trimSymbol);
    }

    public static String ltrim(String string, String trimSymbol) {
        trimSymbol = Pattern.quote(trimSymbol);
        return string.replaceAll("^" + trimSymbol + "+", "");
    }

    public static String rtrim(String string, String trimSymbol) {
        trimSymbol = Pattern.quote(trimSymbol);
        return string.replaceAll(trimSymbol + "+$", "");
    }
}
