package ru.atc.fgislk.ppod.testcore.lklback.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * SenderOrganizationRf
 */
@Getter
@Setter
@Builder
public class SenderOrganizationRf {
  private String name = null;
  private Address address = null;
  private String ogrn = null;
  private String inn = null;
  private String email = null;
  private SenderAttorney attorney = null;
}
