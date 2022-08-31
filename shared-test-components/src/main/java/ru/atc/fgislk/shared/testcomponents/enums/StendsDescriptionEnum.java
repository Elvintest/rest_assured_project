package ru.atc.fgislk.shared.testcomponents.enums;

/**
 * описание полных стендов для подсистем
 * <p>
 *  https://kb.at-consulting.ru/pages/viewpage.action?pageId=302861904
 */
public enum StendsDescriptionEnum {
    /**
     * DEV-стенд
     */
    DEV(PopdStandsEnum.DEV,
            CamundaEnum.DEV,
            PlkStandsEnum.DEV,
            PupStandsEnum.DEV,
            FileStorageEnum.DEV,
            KafkaEnum.DEV,
            PpodLkStendsEnum.DEV),
    UAT(PopdStandsEnum.UAT,
            CamundaEnum.UAT,
            PlkStandsEnum.UAT,
            PupStandsEnum.UAT,
            FileStorageEnum.UAT,
            KafkaEnum.UAT,
            PpodLkStendsEnum.UAT);

    /**
     * ПОПД
     */
    final PopdStandsEnum popd;
    /**
     * Камунда
     */
    final CamundaEnum camunda;

    /**
     * ПЛК
     */
    final PlkStandsEnum plk;
    /**
     * Подсистема управления процессами
     */
    final PupStandsEnum pup;

    /**
     * Файловое хранилище
     */
    final FileStorageEnum fileStorage;
    /**
     * кафка на стенде
     */
    final KafkaEnum kafka;
    /**
     * РРОД личный кабинет
     */
    final PpodLkStendsEnum ppodLk;

    StendsDescriptionEnum(PopdStandsEnum popd, CamundaEnum camunda, PlkStandsEnum plk, PupStandsEnum pup, FileStorageEnum fileStorage, KafkaEnum kafka, PpodLkStendsEnum ppodLk) {
        this.popd = popd;
        this.camunda = camunda;
        this.plk = plk;
        this.pup = pup;
        this.fileStorage = fileStorage;
        this.kafka = kafka;
        this.ppodLk = ppodLk;
    }

    public PopdStandsEnum getPopd() {
        return popd;
    }

    public CamundaEnum getCamunda() {
        return camunda;
    }

    public PlkStandsEnum getPlk() {
        return plk;
    }

    /**
     * Получить данные ПУП стенда
     * @return параметры стенда ПУП
     */
    public PupStandsEnum getPup() {
        return pup;
    }

    public FileStorageEnum getFileStorage() {
        return fileStorage;
    }

    public KafkaEnum getKafka() {
        return kafka;
    }

    /**
     * Получить данные ППОД стенда
     * @return параметры стенда ППОД
     */
    public PpodLkStendsEnum getPpodLk() {
        return ppodLk;
    }

}