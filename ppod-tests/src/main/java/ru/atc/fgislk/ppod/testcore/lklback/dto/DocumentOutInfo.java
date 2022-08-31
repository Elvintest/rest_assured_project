package ru.atc.fgislk.ppod.testcore.lklback.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
/**
 * DocumentOutInfo
 */
@Getter
@Setter
@Builder
public class DocumentOutInfo {
  private String id = null;
  private String type = null;
  private String typeName = null;
  private String fileLink = null;
  private String fileName = null;
  private String fileSigLink = null;
  private String fileSigName = null;
}
