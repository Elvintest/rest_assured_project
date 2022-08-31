package ru.atc.fgislk.shared.testcomponents.camunda;

import java.util.List;

public class ActivityInstance {
    private String id;
    private String activityId;
    private String activityName;
    private String activityType;
    private String processInstanceId;
    private String processDefinitionId;
    private List<ActivityInstance> childActivityInstances;
    private List<String> executionIds;

    public String getId() {
        return id;
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

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public String getProcessDefinitionId() {
        return processDefinitionId;
    }

    public List<ActivityInstance> getChildActivityInstances() {
        return childActivityInstances;
    }

    public List<String> getExecutionIds() {
        return executionIds;
    }
}
