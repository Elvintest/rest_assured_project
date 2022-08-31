package ru.atc.fgislk.ppod.testcore.lklback.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
/**
 * DocumentInfo
 */
@Getter
@Setter
@Builder
public class DocumentInfo {
  private String id = null;
  private String type = null;
  private String kind = null;
  private String kindName = null;
  private String fileLink = null;
  private String fileName = null;
  private String fileSigLink = null;
  private String fileSigName = null;
  private String scale = null;
}
