package ru.atc.fgislk.ppod.testcore.phdfs;

import ru.atc.fgislk.ppod.testcore.phdfs.dtodb.FileMeta;
import ru.atc.fgislk.ppod.testcore.phdfs.dtodb.FilePresence;
import ru.atc.fgislk.shared.testcomponents.enums.StendsDescriptionEnum;
import ru.atc.fgislk.shared.testcomponents.postgresql.PostgreSQL;

import java.sql.SQLException;

public class ConnectDB extends PostgreSQL {

    public ConnectDB(StendsDescriptionEnum stend) throws SQLException {
        super(stend.getPpodLk().getPhdDB().get("path"), stend.getPpodLk().getPhdDB().get("login"), stend.getPpodLk().getPhdDB().get("password"));
    }

    /**
     * Запрос получения файла в таблице
     * @return запис в БД
     */
    public FilePresence selectFile() throws SQLException, InterruptedException, IllegalAccessException, InstantiationException {
        String query = "SELECT x.* FROM public.file_presence x WHERE x.file_id = 'file/file/doctxt';";
        return getOneRecord(FilePresence.class, query, "Проверяем наличие файла в таблице file_presence в БД ПХД");
    }

    /**
     * Запрос к таблицу file_meta
     * @param fileLink путь к файлу
     * @return запис в БД
     */
    public FileMeta selectMeta(String fileLink) throws SQLException, InterruptedException, IllegalAccessException, InstantiationException {
        String query = "SELECT x.* FROM public.file_meta x WHERE x.id = '" + fileLink + "'";
        return getOneRecord(FileMeta.class, query, "Проверяем наличие файла в таблице file_presence в БД ПХД");
    }
}
