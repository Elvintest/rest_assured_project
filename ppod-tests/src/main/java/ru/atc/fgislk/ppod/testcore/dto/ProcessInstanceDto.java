package ru.atc.fgislk.ppod.testcore.dto;

/**
 * dto для экземпляра процесса в камунде
 */
public class ProcessInstanceDto {
    private String[] links;
    private String id;
    private String definitionId;
    private String businessKey;
    private String caseInstanceId;
    private Boolean ended;
    private Boolean suspended;
    private String tenantId;

    public String[] getLinks() {
        return links;
    }

    public String getId() {
        return id;
    }

    public String getDefinitionId() {
        return definitionId;
    }

    public String getBusinessKey() {
        return businessKey;
    }

    public String getCaseInstanceId() {
        return caseInstanceId;
    }

    public Boolean getEnded() {
        return ended;
    }

    public Boolean getSuspended() {
        return suspended;
    }

    public String getTenantId() {
        return tenantId;
    }
}
