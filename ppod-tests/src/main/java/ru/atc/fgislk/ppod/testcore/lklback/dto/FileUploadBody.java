package ru.atc.fgislk.ppod.testcore.lklback.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
/**
 * FileUploadBody
 */
@Getter
@Setter
@Builder
public class FileUploadBody {
  private String file = null;
}
