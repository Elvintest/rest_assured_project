package ru.atc.fgislk.ppod.testcore.lklback.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
/**
 * Period
 */
@Getter
@Setter
@Builder
public class Period {
  private String from = null;
  private String to = null;
}
