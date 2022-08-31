package ru.atc.fgislk.ppod.testcore.indocjournal;

import org.testng.annotations.Optional;
import org.testng.annotations.Test;
import ru.atc.fgislk.shared.testcomponents.enums.StendsDescriptionEnum;

import java.util.HashMap;
import java.util.Map;


public class TestCheckApi {
    @Test(description = "GET Получить ЖУВД по рег.номеру", groups = {"sprint2"})
    void checkApiJournalRegNumber(@Optional("DEV") StendsDescriptionEnum stend) {
        InDocJournal inDocJournal = new InDocJournal(stend);
        Map<String, String> pathParam = new HashMap<>(Map.of("regNumber", "ЖУВД-001/2022"));
        inDocJournal.getJuvdByRegNumber(pathParam).then().assertThat().statusCode(200);
    }

    @Test(description = "Получить все ЖУВД", groups = {"sprint2"})
    void checkApiJournal(@Optional("DEV") StendsDescriptionEnum stend) {
        InDocJournal inDocJournal = new InDocJournal(stend);
        inDocJournal.getJuvdAll().then().assertThat().statusCode(200);
    }

    @Test(description = "Получить запись ЖУВД по номеру", groups = {"sprint2"})
    void checkApiRecordRegNumber(@Optional("DEV") StendsDescriptionEnum stend) {
        InDocJournal inDocJournal = new InDocJournal(stend);
        Map<String, String> pathParam = new HashMap<>(Map.of("regNumber", "ЖУВД-001/2022-00000002"));
        inDocJournal.getRecordJuvdByNumber(pathParam).then().assertThat().statusCode(200);
    }

    @Test(description = "Получить записи журнала", groups = {"sprint2"})
    void checkApiRecordByJournal(@Optional("DEV") StendsDescriptionEnum stend) {
        InDocJournal inDocJournal = new InDocJournal(stend);
        Map<String, String> queryParam = new HashMap<>(Map.of("journal", "ЖУВД-001/2022"));
        inDocJournal.getRecordsByJournal(queryParam).then().assertThat().statusCode(200);
    }

}
