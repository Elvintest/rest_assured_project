package ru.atc.fgislk.ppod.testcore.protocolRegistry.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Запрос на создание протокола
 */
@Getter
@Setter
@Builder
public class ProtocolCreateRequest {
    private String processInstance;
    private String kind;
    private String sourceSubsystem;
}

