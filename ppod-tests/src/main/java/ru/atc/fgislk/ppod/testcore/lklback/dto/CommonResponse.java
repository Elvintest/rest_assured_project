package ru.atc.fgislk.ppod.testcore.lklback.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * CommonResponse
 */
@Getter
@Setter
@Builder
public class CommonResponse {
  private String error = null;
  private String msg = null;
}
