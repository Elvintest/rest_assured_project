package ru.atc.fgislk.ppod.testcore.lklback.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Coordinate
 */
@Getter
@Setter
@Builder
public class Coordinate {
  private Integer index = null;
  private Float longitude = null;
  private Float latitude = null;
}
