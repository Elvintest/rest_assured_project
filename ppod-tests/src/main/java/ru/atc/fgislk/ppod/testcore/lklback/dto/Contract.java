package ru.atc.fgislk.ppod.testcore.lklback.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Contract
 */
@Getter
@Setter
@Builder
public class Contract {
  private String type = null;
  private String number = null;
  private String date = null;
}
