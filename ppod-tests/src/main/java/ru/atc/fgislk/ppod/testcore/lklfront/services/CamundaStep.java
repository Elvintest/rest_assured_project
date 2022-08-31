package ru.atc.fgislk.ppod.testcore.lklfront.services;

import ru.atc.fgislk.shared.testcomponents.camunda.CamundaService;
import ru.atc.fgislk.shared.testcomponents.enums.StendsDescriptionEnum;

public class CamundaStep extends CamundaService {
    public CamundaStep(StendsDescriptionEnum stend) {
        super(stend.getCamunda().getHeartbeat());
    }
}
