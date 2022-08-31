package ru.atc.fgislk.ppod.testcore.lklback.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * SenderForeignOrganization
 */
@Getter
@Setter
@Builder
public class SenderForeignOrganization {
  private String name = null;
  private Address address = null;
  private String inn = null;
  private String kio = null;
  private String email = null;
  private SenderAttorney attorney = null;
}
