package ru.atc.fgislk.ppod.testcore.lklback.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * SendApplicationRequest
 */
@Getter
@Setter
@Builder
public class SendApplicationRequest {
  private SenderData sender = null;
}
