package ru.atc.fgislk.ppod.testcore.lklback.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * StatusHistory
 */
@Getter
@Setter
@Builder
public class StatusHistory {
  private String createdAt = null;
  private String status = null;
  private String statusName = null;
}
