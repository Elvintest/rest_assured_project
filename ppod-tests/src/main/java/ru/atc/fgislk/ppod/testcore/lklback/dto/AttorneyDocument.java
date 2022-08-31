package ru.atc.fgislk.ppod.testcore.lklback.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * AttorneyDocument
 */
@Getter
@Setter
@Builder
@Accessors(chain = true)
public class AttorneyDocument {
  private String name = null;
  private String number = null;
  private String docDate = null;
  private String issuer = null;
}
