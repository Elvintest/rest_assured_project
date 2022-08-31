package ru.atc.fgislk.ppod.testcore.lklback.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Singular;

import java.util.List;
/**
 * DeclarationInfo
 */
@Getter
@Setter
@Builder
public class DeclarationInfo {
  private String number = null;
  private String date = null;
  private ExecutiveAuthority executiveAuthority = null;
  private Period period = null;
  private Contract contract = null;
  @Singular
  private List<UsageType> usageTypes = null;
  private Forestry forestry = null;
}
