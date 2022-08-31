package ru.atc.fgislk.ppod.testcore.lklback.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Singular;

import java.util.List;
/**
 * ApplicationSaveRequest
 */
@Getter
@Setter
@Builder
public class ApplicationSaveRequest {
  private String id = null;
  private String applicationKind = null;
  @Singular
  private List<Subject> subjects = null;
  private DeclarationInfo declarationInfo = null;
  private Region region = null;
}
