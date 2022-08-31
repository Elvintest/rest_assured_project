package ru.atc.fgislk.ppod.testcore.protocolRegistry;

import ru.atc.fgislk.shared.testcomponents.back.BaseRestService;
import ru.atc.fgislk.shared.testcomponents.enums.StendsDescriptionEnum;

public class CloseProtocol extends BaseRestService {
    public CloseProtocol(StendsDescriptionEnum stend) {
        super(stend.getPpodLk().getProtocolRegistry());
    }

}