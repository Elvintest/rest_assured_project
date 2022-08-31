package ru.atc.fgislk.ppod.testcore.lklback.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * SenderPerson
 */
@Getter
@Setter
@Builder
public class SenderPerson {
  private String lastname = null;
  private String firstname = null;
  private String patronymic = null;
  private Address address = null;
  private IdDocument idDocument = null;
  private String snils = null;
  private String inn = null;
  private String phone = null;
  private String email = null;
}
