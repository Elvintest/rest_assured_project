package ru.atc.fgislk.ppod.testcore.lklback.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
/**
 * EmployeeInfo
 */
@Getter
@Setter
@Builder
public class EmployeeInfo {
  private String lastname = null;
  private String firstname = null;
  private String patronymic = null;
  private String post = null;
  private String postGenitive = null;
  private String fioGenitive = null;
  private String basisAuthority = null;
  private String basisAuthorityGenitive = null;
  private String number = null;
  private String date = null;
  private String phone = null;
}
