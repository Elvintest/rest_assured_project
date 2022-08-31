package ru.atc.fgislk.ppod.testcore.indocjournal.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
/**
 * Сведения о документе
 */
@Getter
@Setter
@Builder
public class DocumentInfo {
  private String type = null;
  private String name = null;
  private String number = null;
  private String date = null;
}
