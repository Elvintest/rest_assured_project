package ru.atc.fgislk.ppod.testcore.lklback.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * XmlResponse
 */
@Getter
@Setter
@Builder
public class XmlResponse {
  private String applicationXml = null;
  private String error = null;
}
