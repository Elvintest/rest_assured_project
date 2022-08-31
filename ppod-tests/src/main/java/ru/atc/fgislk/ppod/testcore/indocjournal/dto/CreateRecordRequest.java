package ru.atc.fgislk.ppod.testcore.indocjournal.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Запрос на создание записи ЖУВД
 */
@Getter
@Setter
@Builder
public class CreateRecordRequest {
  private String sourceSystem = null;
  private String deliveryChannel = null;
  private DocumentInfo document = null;
}
