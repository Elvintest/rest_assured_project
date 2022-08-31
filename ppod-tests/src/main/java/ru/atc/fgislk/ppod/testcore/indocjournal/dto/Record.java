package ru.atc.fgislk.ppod.testcore.indocjournal.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Запись ЖУВД
 */
@Getter
@Setter
@Builder
public class Record {
  private Long id = null;
  private String regNumber = null;
  private String sourceSystem = null;
  private String deliveryChannel = null;
  private DocumentInfo document = null;
  private String createdAt = null;
}
