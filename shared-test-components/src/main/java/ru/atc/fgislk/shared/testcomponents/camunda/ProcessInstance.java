package ru.atc.fgislk.shared.testcomponents.camunda;

public class ProcessInstance {

    //List<String> links;
    private String id;
    private String definitionId;
    private String businessKey;
    private String caseInstanceId;
    private boolean ended;
    private boolean suspended;
    private String tenantId;

//    public List<String> getLinks() {
//        return links;
//    }

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

    public boolean isEnded() {
        return ended;
    }

    public boolean isSuspended() {
        return suspended;
    }

    public String getTenantId() {
        return tenantId;
    }
}
