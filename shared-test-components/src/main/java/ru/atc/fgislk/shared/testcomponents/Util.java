package ru.atc.fgislk.shared.testcomponents;

import com.google.gson.*;
import io.qameta.allure.Allure;
import org.apache.commons.io.FileUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Header;
import org.testng.util.Strings;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.testng.util.Strings.isNullOrEmpty;

public class Util {
    /**
     * Статический класс Random()
     */
    public static final Random RANDOM = new Random();
    /**
     * Сериализация json
     */
    public static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(ZonedDateTime.class, (JsonDeserializer<ZonedDateTime>)
                    (json, typeOfT, context) -> ZonedDateTime.parse(json.getAsString(), DateTimeFormatter.ISO_OFFSET_DATE_TIME))
            .registerTypeAdapter(ZonedDateTime.class, (JsonSerializer<ZonedDateTime>)
                    (date, typeOfSrc, context) -> new JsonPrimitive(date.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)))
            .create();

    /**
     * Соединяет все аргументы, через разделитель, игнорируя аргументы пустые строки и NULL
     *
     * @param delimiter разделитель
     * @param lexems    аргументы для соединения
     * @return если все lexems=null, то null. Иначе конкатенацию непустых аргументов через разделитель
     */
    public static String concat_ws(String delimiter, String... lexems) {
        String result = null;
        for (String lexem : lexems) {
            if (isNullOrEmpty(lexem))
                continue;
            if (isNullOrEmpty(result)) {
                result = lexem;
                continue;
            }
            result += delimiter + lexem;
        }
        return result;
    }

    /**
     * Вывести строку в лог аллюр.
     * Если один из параметров пустой или null, то лог не формируется
     *
     * @param name    название параметра
     * @param content значение параметра
     */
    public static void stringToAllureLog(String name, String content) {
        if (!Strings.isNullOrEmpty(name) && !Strings.isNullOrEmpty(content))
            Allure.attachment(name, content);
    }

    /**
     * Получить файл из ресурсов и сохранить в строку, кодировка UTF-8
     *
     * @param fileName файл в ресурсах
     * @return содержимое файла как строка
     * @throws IOException
     */
    public static String readFileToString(String fileName) throws IOException, URISyntaxException {
        return FileUtils.readFileToString(
                new File(Objects.requireNonNull(
                        Util.class.getClassLoader().getResource(fileName)).toURI()),
                StandardCharsets.UTF_8);
    }

    /**
     * получить заголовки сообщения kafka
     *
     * @param record сообщение
     * @return заголовки
     */
    public static Map<String, String> getRecordHeaders(ConsumerRecord<String, String> record) {
        Map<String, String> headers = new TreeMap<>();
        for (Header h : record.headers())
            headers.put(h.key(), new String(h.value(), StandardCharsets.UTF_8));
        return headers;
    }

    /**
     * из заголовков получить строку для вывода в лог
     *
     * @param map
     * @return строка вида key1=value1; key2=value2
     */
    public static String mapHeadersToLogString(Map<String, String> map) {
        StringBuilder buf = new StringBuilder();
        map.entrySet().forEach(p -> buf.append(p.toString()).append("; "));
        return buf.toString().trim();
    }

    /**
     * если дата NULL, то вернуть null
     *
     * @param offsetDateTime
     * @return значение даты или если дата NULL, то вернуть null
     */
    public static OffsetDateTime getOffsetDateTime(Timestamp offsetDateTime) {
        return offsetDateTime == null ? null : OffsetDateTime.ofInstant(offsetDateTime.toInstant(), ZoneId.systemDefault());
    }

    /**
     * если дата NULL, то вернуть null
     *
     * @param date
     * @return значение даты или если дата NULL, то вернуть null
     */
    public static LocalDate getLocalDate(Date date) {
        return date == null ? null : date.toLocalDate();
    }

    /**
     * получить случайный элемент из списка
     *
     * @param list список
     * @param <T>  тип элемнта списка
     * @return элемент списка
     */
    public static <T> T getAny(List<T> list) {
        return list.get(RANDOM.nextInt(list.size()));
    }

}
