package ru.atc.fgislk.ppod.testcore.indocjournal;
// http://10.125.4.181:30003/swagger-ui/index.html#/InDocJournal/getJournals

/**
 * Журнал учёта входящих документов (ЖУВД)
 */
public class EndPoints {
    // ------------------------ InDocJournal
    /**
     * GET Получить ЖУВД по рег.номеру
     */
    public static final String getJuvdByRegNumber = "/journal/{regNumber}";
    /**
     * GET Получить все ЖУВД
     */
    public static final String getJuvdAll = "/journal/";

    // ------------------------ InDocJournalRecord
    /**
     * GET Получить запись ЖУВД по номеру
     */
    public static final String getRecordJuvdByNumber = "/record/{regNumber}";
    /**
     * GET Получить записи журнала
     */
    public static final String getRecordsByJournal = "/record/byJournal";
    /**
     * POST Получить записи журнала
     */
    public static final String postCreateRecotdJuvd = "/record";
    /**
     * PUT Внести результат в запись ЖУВД
     */
    public static final String putResultRecordjuvd = "/record/{regNumber}/result";
}
