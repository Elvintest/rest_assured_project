package ru.atc.fgislk.shared.testcomponents.camunda;

import java.time.OffsetDateTime;

/**
 * класс описывает результат, возвращаемый методом history/process-instance/{id}
 */
public class HistoryProcessInstance {
    private String id;
    private String businessKey;
    private String processDefinitionId;
    private String processDefinitionKey;
    private String processDefinitionName;
    private OffsetDateTime startTime;
    private OffsetDateTime endTime;
    private String state;

    public String getId() {
        return id;
    }

    public String getBusinessKey() {
        return businessKey;
    }

    public String getProcessDefinitionId() {
        return processDefinitionId;
    }

    public String getProcessDefinitionKey() {
        return processDefinitionKey;
    }

    public String getProcessDefinitionName() {
        return processDefinitionName;
    }

    public OffsetDateTime getStartTime() {
        return startTime;
    }

    public OffsetDateTime getEndTime() {
        return endTime;
    }

    public String getState() {
        return state;
    }

}
