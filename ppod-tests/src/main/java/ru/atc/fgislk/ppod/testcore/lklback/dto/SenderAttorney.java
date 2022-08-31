package ru.atc.fgislk.ppod.testcore.lklback.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * SenderAttorney
 */
@Getter
@Setter
@Builder
public class SenderAttorney {
  private String lastname = null;
  private String firstname = null;
  private String patronymic = null;
  private String jobTitle = null;
  private String snils = null;
  private String inn = null;
  private String email = null;
  private String phone = null;
  private AttorneyDocument attorneyDocument = null;
  private IdDocument idDocument = null;
  private Address address = null;
}
