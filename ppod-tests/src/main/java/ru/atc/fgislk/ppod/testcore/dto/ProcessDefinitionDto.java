package ru.atc.fgislk.ppod.testcore.dto;

/**
 * dto для экземпляра процесса в камунде
 */
public class ProcessDefinitionDto {
    private String id;
    private String key;
    private String category;
    private String description;
    private String name;
    private int version;
    private String resource;
    private String deploymentId;
    private String diagram;
    private Boolean suspended;
    private String tenantId;
    private String versionTag;
    private String historyTimeToLive;
    private Boolean startableInTasklist;

    public String getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public int getVersion() {
        return version;
    }

    public String getResource() {
        return resource;
    }

    public String getDeploymentId() {
        return deploymentId;
    }

    public String getDiagram() {
        return diagram;
    }

    public Boolean getSuspended() {
        return suspended;
    }

    public String getTenantId() {
        return tenantId;
    }

    public String getVersionTag() {
        return versionTag;
    }

    public String getHistoryTimeToLive() {
        return historyTimeToLive;
    }

    public Boolean getStartableInTasklist() {
        return startableInTasklist;
    }
}
