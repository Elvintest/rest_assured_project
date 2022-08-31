package ru.atc.fgislk.ppod.testcore.lklback.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Singular;

import java.util.List;
/**
 * ApplicationsInfoWithCount
 */
@Getter
@Setter
@Builder
public class ApplicationsInfoWithCount {
  private Long total = null;
  @Singular
  private List<ApplicationShortInfo> items = null;
}
