package ru.atc.fgislk.pup.testcore.controlprocessproxy;

import ru.atc.fgislk.shared.testcomponents.back.CommonBackMethod;
import ru.atc.fgislk.shared.testcomponents.enums.StendsDescriptionEnum;

/**
 * Управление развертыванием бизнес процессов
 */
public class PupControlProcessProxy extends CommonBackMethod {
    public PupControlProcessProxy(StendsDescriptionEnum stend) {
        super(stend.getPup().getControlProcessProxy());
    }
}
