package ru.atc.fgislk.ppod.testcore.lklback.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * IdDocument
 */
@Getter
@Setter
@Builder
public class IdDocument {
  private DictionaryItem type = null;
  private String series = null;
  private String number = null;
  private String issueName = null;
  private String issueCode = null;
  private String issueDate = null;
}
