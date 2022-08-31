package ru.atc.fgislk.ppod.testcore.lklback.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Singular;

import java.util.List;
/**
 * ForestObjectShortWithCount
 */
@Getter
@Setter
@Builder
public class ForestObjectShortWithCount {
  private Long total = null;
  @Singular
  private List<ForestObjectShort> items = null;
}
