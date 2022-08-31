package ru.atc.fgislk.shared.testcomponents.camunda;

public class ExternalTask {
    public String activityId;
    public String activityInstanceId;
    public String errorMessage;
    public String errorDetails;
    public String executionId;
    public String id;
    public String lockExpirationTime;
    public String processDefinitionId;
    public String processDefinitionKey;
    public String processDefinitionVersionTag;
    public String processInstanceId;
    public String retries;
    public String workerId;
    public String topicName;
    public String tenantId;
    public int priority;
    public String businessKey;

    public String getId() {
        return id;
    }

    public String getActivityId() {
        return activityId;
    }

    public String getActivityInstanceId() {
        return activityInstanceId;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getErrorDetails() {
        return errorDetails;
    }

    public String getExecutionId() {
        return executionId;
    }

    public String getLockExpirationTime() {
        return lockExpirationTime;
    }

    public String getProcessDefinitionId() {
        return processDefinitionId;
    }

    public String getProcessDefinitionKey() {
        return processDefinitionKey;
    }

    public String getProcessDefinitionVersionTag() {
        return processDefinitionVersionTag;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public String getRetries() {
        return retries;
    }

    public String getWorkerId() {
        return workerId;
    }

    public String getTopicName() {
        return topicName;
    }

    public String getTenantId() {
        return tenantId;
    }

    public int getPriority() {
        return priority;
    }

    public String getBusinessKey() {
        return businessKey;
    }
}
