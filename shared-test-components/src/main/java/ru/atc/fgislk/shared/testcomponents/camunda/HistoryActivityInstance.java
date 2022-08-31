package ru.atc.fgislk.shared.testcomponents.camunda;

import java.time.OffsetDateTime;

public class HistoryActivityInstance {
    private String id;
    private String parentActivityInstanceId;
    private String activityId;
    private String activityName;
    private String activityType;
    private String processDefinitionKey;
    private String processDefinitionId;
    private String processInstanceId;
    private String executionId;
    private String assignee;
    private OffsetDateTime startTime;
    private OffsetDateTime endTime;
    private Long durationInMillis;

    public String getId() {
        return id;
    }

    public String getParentActivityInstanceId() {
        return parentActivityInstanceId;
    }

    public String getActivityId() {
        return activityId;
    }

    public String getActivityName() {
        return activityName;
    }

    public String getActivityType() {
        return activityType;
    }

    public String getProcessDefinitionKey() {
        return processDefinitionKey;
    }

    public String getProcessDefinitionId() {
        return processDefinitionId;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public String getExecutionId() {
        return executionId;
    }

    public String getAssignee() {
        return assignee;
    }

    public OffsetDateTime getStartTime() {
        return startTime;
    }

    public OffsetDateTime getEndTime() {
        return endTime;
    }

    public Long getDurationInMillis() {
        return durationInMillis;
    }
}
