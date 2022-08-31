package ru.atc.fgislk.ppod.testcore.lklfront.services;

import ru.atc.fgislk.shared.testcomponents.back.BaseRestService;
import ru.atc.fgislk.shared.testcomponents.enums.StendsDescriptionEnum;

public class LklFront extends BaseRestService {
    public LklFront(StendsDescriptionEnum stend) {
        super(stend.getPpodLk().getLklFront());
    }

}
