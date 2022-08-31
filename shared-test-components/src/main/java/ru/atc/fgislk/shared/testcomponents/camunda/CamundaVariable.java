package ru.atc.fgislk.shared.testcomponents.camunda;

import java.util.Arrays;
import java.util.List;

import static org.testng.util.Strings.isNullOrEmpty;

/**
 * Переменные в camunda
 *
 * Дополнительно ввел поле name, которое не возвращается в запросах, но облегчает использование в коде тестов
 */
public class CamundaVariable {
    private String type;
    private String value;

    /**
     * дополнительно для удобства ввожу еще одно поле - название переменной. Штатно камундой не возвращается, но значительно облегчает работу с переменными
     */
    private String name;

    public CamundaVariable(String name, String type, String value) {
        this.type = type;
        this.value = value;
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.format("\"%s\":{\"value\":\"%s\",\"type\":\"%s\"}", name, value, type);
    }

    /**
     * получить из списка переменных камунды строку
     *
     * @param vars список переменных
     * @return строка для использования в ДТО для комплита сервистаска или юзертаска
     */
    public static String varsToString(List<CamundaVariable> vars) {
        String result = null;
        for (CamundaVariable var : vars) {
            if (isNullOrEmpty(result)) {
                result = var.toString();
                continue;
            }
            result += "," + var.toString();
        }
        return result;
    }

    /**
     * получить из списка переменных камунды строку
     *
     * @param vars список переменных
     * @return строка для использования в ДТО для комплита сервистаска или юзертаска
     */
    public static String varsToString(CamundaVariable... vars) {
        if (vars.length == 0)
            return null;
        return varsToString(Arrays.asList(vars));
    }
}
