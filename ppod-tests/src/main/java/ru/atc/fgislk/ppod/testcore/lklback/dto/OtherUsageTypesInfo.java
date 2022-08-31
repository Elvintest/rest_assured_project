package ru.atc.fgislk.ppod.testcore.lklback.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Singular;

import java.math.BigDecimal;
import java.util.List;
/**
 * OtherUsageTypesInfo
 */
@Getter
@Setter
@Builder
public class OtherUsageTypesInfo {
  private DictionaryItem resource = null;
  @Singular("oneCuttingInfo")
  private List<CuttingInfo> cuttingInfo = null;
  private BigDecimal area = null;
  private BigDecimal volume = null;
  private DictionaryItem unitType = null;
}
