package ru.atc.fgislk.ppod.testcore.lklback.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * SaveXmlSigRequest
 */
@Getter
@Setter
@Builder
public class SaveXmlSigRequest {
  private String signatureBase64 = null;
}
