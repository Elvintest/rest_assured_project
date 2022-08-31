package ru.atc.fgislk.ppod.testcore.protocolRegistry;

import ru.atc.fgislk.shared.testcomponents.back.BaseRestService;
import ru.atc.fgislk.shared.testcomponents.enums.StendsDescriptionEnum;

public class Task extends BaseRestService {
    public Task(StendsDescriptionEnum stend)  {
        super(stend.getPpodLk().getProtocolRegistry());
    }
}