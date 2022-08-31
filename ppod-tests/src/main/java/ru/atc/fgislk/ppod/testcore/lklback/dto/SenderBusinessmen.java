package ru.atc.fgislk.ppod.testcore.lklback.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * SenderBusinessmen
 */
@Getter
@Setter
@Builder
public class SenderBusinessmen {
  private String lastname = null;
  private String firstname = null;
  private String patronymic = null;
  private String ogrnip = null;
  private String inn = null;
  private SenderAttorney attorney = null;
}
