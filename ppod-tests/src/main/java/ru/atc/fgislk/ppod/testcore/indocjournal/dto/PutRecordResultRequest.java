package ru.atc.fgislk.ppod.testcore.indocjournal.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Запрос на внесение результата в запись ЖУВД
 */
@Getter
@Setter
@Builder
public class PutRecordResultRequest {
  private String description = null;
  private String fixedAt = null;
  private DocumentInfo document = null;
}
