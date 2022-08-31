package ru.atc.fgislk.ppod.testcore.lklback.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * ApplicationSaveResponse
 */
@Getter
@Setter
@Builder
public class ApplicationSaveResponse {
  private String id = null;
  private String error = null;
}

