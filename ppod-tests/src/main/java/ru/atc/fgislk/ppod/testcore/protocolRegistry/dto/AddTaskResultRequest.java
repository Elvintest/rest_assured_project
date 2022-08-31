package ru.atc.fgislk.ppod.testcore.protocolRegistry.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * Запрос на добавление результата задачи (в дальнейшем будет запускаться отдельный Control-service для проверки)
 */
@Getter
@Setter
@Builder
public class AddTaskResultRequest {
    private String result;
    private String reason;
    private Map<String, String> details;
    private String sourceSubsystem;
    private String user;
}
