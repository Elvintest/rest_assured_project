package ru.atc.fgislk.ppod.testcore.lklback.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * DictionaryItem
 */
@Getter
@Setter
@Builder
public class DictionaryItem {
  private String code = null;
  private String title = null;
}
