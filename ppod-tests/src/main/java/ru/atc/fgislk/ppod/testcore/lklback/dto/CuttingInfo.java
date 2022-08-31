package ru.atc.fgislk.ppod.testcore.lklback.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * CuttingInfo
 */
@Getter
@Setter
@Builder
public class CuttingInfo {
    private DictionaryItem tree = null;
    private String farm = null;
    private DictionaryItem typeCutting = null;
}
