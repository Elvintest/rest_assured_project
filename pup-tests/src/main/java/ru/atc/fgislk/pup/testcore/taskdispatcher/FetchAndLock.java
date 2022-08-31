package ru.atc.fgislk.pup.testcore.taskdispatcher;

import ru.atc.fgislk.shared.testcomponents.camunda.ExternalTask;

public class FetchAndLock {

    private String businessKey;
    private ExternalTask message;
    private String typeRequest;
    private String replyTo;
    private boolean answered;

    public String getBusinessKey() {
        return businessKey;
    }

    public ExternalTask getMessage() {
        return message;
    }

    public String getTypeRequest() {
        return typeRequest;
    }

    public String getReplyTo() {
        return replyTo;
    }

    public boolean isAnswered() {
        return answered;
    }
}
