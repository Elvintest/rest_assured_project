package ru.atc.fgislk.ppod.testcore.indocjournal.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * информация о сортировке элементов
 */
@Getter
@Setter
@Builder
public class Sort {
  private Boolean sorted = null;
  private Boolean unsorted = null;
  private Boolean empty = null;
}
