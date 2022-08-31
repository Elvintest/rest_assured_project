package ru.atc.fgislk.ppod.testcore.lklback.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * SenderData
 */
@Getter
@Setter
@Builder
public class SenderData {
  private SenderPerson person = null;
  private SenderOrganizationRf organizationRf = null;
  private SenderForeignOrganization foreignOrganization = null;
  private SenderBusinessmen businessman = null;
}
