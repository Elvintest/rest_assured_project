package ru.atc.fgislk.ppod.testcore.protocolRegistry.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * Запрос на создание протокола
 */
@Getter
@Setter
@Builder
public class AddTaskRequest {
    private String sourceSubsystem;
    private String controlRule;
    private String criticality;
    private Map<String, String> objectInfo;
}
