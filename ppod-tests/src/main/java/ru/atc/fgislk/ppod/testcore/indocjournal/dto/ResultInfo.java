package ru.atc.fgislk.ppod.testcore.indocjournal.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * сведения о результате обработки входящего документа
 */
@Getter
@Setter
@Builder
public class ResultInfo {
  private String description = null;
  private String fixedAt = null;
  private DocumentInfo document = null;
  private String createdAt = null;
}
