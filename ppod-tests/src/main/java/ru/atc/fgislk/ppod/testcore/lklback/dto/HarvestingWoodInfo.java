package ru.atc.fgislk.ppod.testcore.lklback.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
/**
 * HarvestingWoodInfo
 */
@Getter
@Setter
@Builder
public class HarvestingWoodInfo {
  private String farm = null;
  private String formCutting = null;
  private DictionaryItem typeCutting = null;
  private DictionaryItem tree = null;
  private BigDecimal area = null;
  private BigDecimal volume = null;
  private DictionaryItem unitType = null;
}
