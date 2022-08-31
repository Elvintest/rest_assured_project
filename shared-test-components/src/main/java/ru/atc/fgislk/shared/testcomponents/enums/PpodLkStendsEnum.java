package ru.atc.fgislk.shared.testcomponents.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * Сервисы Подстистемы обработки пространственных данных Личный кабинет
 */
public enum PpodLkStendsEnum {
    /**
     * DEV-стенд
     */
    DEV(
            "http://frontend.fgis-ppod-lkl-external.dev.fgislk.at-consulting.ru",
            FileStorageEnum.DEV.getFileStorageUrl(),
            "http://back.lkl.dev.fgislk.at-consulting.ru",
            "http://frontend.fgis-ppod-lkl-external.dev.fgislk.at-consulting.ru",
            "http://in-doc-journal.ppod.dev.fgislk.at-consulting.ru",
            "http://protocol-registry.ppod.dev.fgislk.at-consulting.ru",
            new HashMap<>(Map.of(
                    "path", "jdbc:postgresql://10.125.4.112:5432/fsmetadb",
                    "login", "fsuser",
                    "password", "fsuserpwd"
            )
            ),
            new HashMap<>(Map.of(
                    "path", "jdbc:postgresql://10.125.4.102:5432/fgis_lkl",
                    "login", "fgis_lkl",
                    "password", "fgis_lkl"
            )
            ),
            new HashMap<>(Map.of(
                    "path", "jdbc:postgresql://10.125.4.102:5432/ppod",
                    "login", "ppod",
                    "password", "ppod"
            )
            ),
            new HashMap<>(Map.of(
                    "path", "jdbc:postgresql://10.125.4.102:5432/ppod_input_doc_journal",
                    "login", "ppod_input_doc_journal",
                    "password", "ppod_input_doc_journal"
            )
            ),
            new HashMap<>(Map.of(
                    "path", "jdbc:postgresql://10.125.4.102:5432/ppod_output_doc_journal",
                    "login", "ppod_output_doc_journal",
                    "password", "ppod_output_doc_journal"
            )
            ),
            new HashMap<>(Map.of(
                    "path", "jdbc:postgresql://10.125.4.102:5432/ppod_protocol",
                    "login", "ppod_protocol",
                    "password", "ppod_protocol"
            )
            ),
            new HashMap<>(Map.of(
                    "path", "jdbc:postgresql://10.125.4.102:5432/doc_package_registry?currentSchema=file_set",
                    "login", "doc_package_registry",
                    "password", "doc_package_registry"
            )
            ),
            new HashMap<>(Map.of(
                    "path", "jdbc:postgresql://10.125.4.102:5432/doc_package_registry",
                    "login", "doc_package_registry",
                    "password", "doc_package_registry"
            )
            ),
            new HashMap<>(Map.of(
                    "path", "jdbc:postgresql://10.125.4.102:5432/ppod_doc_package",
                    "login", "ppod_doc_package",
                    "password", "ppod_doc_package"
            )
            )
    ),
    UAT(
            "http://front.lkl.uat.fgislk.at-consulting.ru/",
            "http://map-api.plk.uat.fgislk.at-consulting.ru",
            "http://back.lkl.uat.fgislk.at-consulting.ru/api",
            "http://front.lkl.uat.fgislk.at-consulting.ru",
            "http://in-doc-journal.ppod.dev.fgislk.at-consulting.ru",
            "http://protocol-registry.ppod.dev.fgislk.at-consulting.ru", // no resource for uat yet
            new HashMap<>(Map.of(
                    "path", "jdbc:postgresql://10.125.7.33:5432/fsmetadb",
                    "login", "fsuser",
                    "password", "F{oUwwuj~VyX"
            )
            ),
            new HashMap<>(Map.of(
                    "path", "jdbc:postgresql://10.125.7.30:5432/fgis_lkl",
                    "login", "fgis_lkl",
                    "password", "aW1s8CZRxmTl"
            )
            ),
            new HashMap<>(Map.of(
                    "path", "jdbc:postgresql://10.125.7.6:5432/ppod",
                    "login", "ppod",
                    "password", "R2AiYuAOZ5V"
            )
            ),
            new HashMap<>(Map.of(
                    "path", "jdbc:postgresql://10.125.7.6:5432/ppod_input_doc_journal",
                    "login", "ppod_input_doc_journal",
                    "password", "jigI$HG8F{qY"
            )
            ),
            new HashMap<>(Map.of(
                    "path", "jdbc:postgresql://10.125.7.6:5432/ppod_output_doc_journal",
                    "login", "ppod_output_doc_journal",
                    "password", "axqoP8O3OjCU"
            )
            ),
            new HashMap<>(Map.of(
                    "path", "jdbc:postgresql://10.125.7.6:5432/ppod_protocol",
                    "login", "ppod_protocol",
                    "password", "WA1TdsW{X48s"
            )
            ),
            new HashMap<>(Map.of(                                                      // no resource for uat yet
                    "path", "jdbc:postgresql://10.125.7.6:5432/ppod_protocol",
                    "login", "ppod_protocol",
                    "password", "WA1TdsW{X48s"
            )
            ),
            new HashMap<>(),
            new HashMap<>()
    );
    /**
     * Фронт личного кабинета
     */
    final String fronLk;
    /**
     * Файловое хранилище
     */
    final String fileStorage;
    /**
     * Lkl back
     */
    final String lklBack;
    /**
     * Lkl front
     */
    final String lklFront;

    final String juvd;
    /**
     * сервис создания и хранения протоколов
     */
    final String protocolRegistry;
    /**
     * Подключения к БД ПХД
     */
    final Map<String, String> phdDB;
    /**
     * Строка подключения к БД lkl-back
     */
    final Map<String, String> lklBackBD;
    /**
     * Строка подключения к БД ControlService
     */
    final Map<String, String> controlServiceBD;
    /**
     * Строка подключения к БД ControlService
     */
    final Map<String, String> inDocJournalBD;
    /**
     * Строка подключения к БД OutDocJournal
     */
    final Map<String, String> outDocJournalBD;
    /**
     * Строка подключения к БД StatusRegistry
     */
    final Map<String, String> statusRegistryBD;
    /**
     * Строка подключения к БД FilesetRegistry
     */
    final Map<String, String> filesetRegistryBD;
    /**
     * Строка подключения к БД DocPackageRegistry
     */
    final Map<String, String> docPackageRegistryBD;
    /**
     * Строка подключения к БД DocPackageService
     */
    final  Map<String, String> docPackageServiceBD;


    PpodLkStendsEnum(String fronLk, String fileStorage, String lklBack, String lklFront,
                     String juvd, String protocolRegistry, Map<String, String> phdDB,
                     Map<String, String> lklBackBD, Map<String, String> controlServiceBD,
                     Map<String, String> inDocJournalBD, Map<String, String> outDocJournalBD,
                     Map<String, String> statusRegistryBD, Map<String, String> filesetRegistryBD,
                     Map<String, String> docPackageRegistryBD,Map<String, String> docPackageServiceBD) {
        this.fronLk = fronLk;
        this.fileStorage = fileStorage;
        this.lklBack = lklBack;
        this.lklFront = lklFront;
        this.juvd = juvd;
        this.protocolRegistry = protocolRegistry;
        this.phdDB = phdDB;
        this.lklBackBD = lklBackBD;
        this.controlServiceBD = controlServiceBD;
        this.inDocJournalBD = inDocJournalBD;
        this.outDocJournalBD = outDocJournalBD;
        this.statusRegistryBD = statusRegistryBD;
        this.filesetRegistryBD = filesetRegistryBD;
        this.docPackageRegistryBD = docPackageRegistryBD;
        this.docPackageServiceBD = docPackageServiceBD;
    }

    public String getFronLk() {
        return fronLk;
    }

    public String getFileStorage() {
        return fileStorage;
    }

    public String getLklBack() {
        return lklBack;
    }

    public String getLklFront() {
        return lklFront;
    }

    public String getJuvd() {
        return juvd;
    }

    public String getProtocolRegistry() {
        return protocolRegistry;
    }

    public Map<String, String> getPhdDB() {
        return phdDB;
    }

    public Map<String, String> getLklBackBD() {
        return lklBackBD;
    }

    public Map<String, String> getControlServiceBD() {
        return controlServiceBD;
    }

    public Map<String, String> getInDocJournalBD() {
        return inDocJournalBD;
    }

    public Map<String, String> getOutDocJournalBD() {
        return outDocJournalBD;
    }

    public Map<String, String> getStatusRegistryBD() {
        return statusRegistryBD;
    }

    public Map<String, String> getFilesetRegistryBD() {
        return filesetRegistryBD;
    }

    public Map<String, String> getDocPackageRegistryBD() {
        return docPackageRegistryBD;
    }
    public Map<String, String> getDocPackageServiceBD(){
        return docPackageServiceBD;
    }
}