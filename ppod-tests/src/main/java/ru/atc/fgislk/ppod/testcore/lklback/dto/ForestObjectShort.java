package ru.atc.fgislk.ppod.testcore.lklback.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * ForestObjectShort
 */
@Getter
@Setter
@Builder
public class ForestObjectShort {
  private String id = null;
  private String forestryId = null;
  private String forestryName = null;
  private String subforestryId = null;
  private String subforestryName = null;
  private String tractId = null;
  private String tractName = null;
  private String quarter = null;
  private String taxationUnit = null;
  private String cuttingArea = null;
  private String usageType = null;
  private BigDecimal area = null;
  private BigDecimal volume = null;
  private String unitTypeId = null;
  private String unitTypeName = null;
  private String createDate = null;
}
