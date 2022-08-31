package ru.atc.fgislk.ppod.testcore.lklback.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
//@Accessors(chain = true) // возвращают this
public class Address {
  private String fiasId = null;
  private String region = null;
  private String countryCode = null;
  private String note = null;
}
