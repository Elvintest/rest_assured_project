package ru.atc.fgislk.pup.testcore.pupcamunda;

import ru.atc.fgislk.shared.testcomponents.back.CommonBackMethod;
import ru.atc.fgislk.shared.testcomponents.enums.StendsDescriptionEnum;

/**
 * пока под этим модулем понимаем camunda-heartbeat
 */
public class PupCamunda extends CommonBackMethod {
    public PupCamunda(String apiUrl) {
        super(apiUrl);
    }
    public PupCamunda(StendsDescriptionEnum stend) {
        this(stend.getCamunda().getHeartbeat());

    }
}
