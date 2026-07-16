package com.vinayak.policyapi.ppolicy_api.models;

import java.time.LocalDateTime;

public class Audit {
    private int auditId;
    private String actionDescription;
    private LocalDateTime actionTimestamp;

    public Audit() {
    }

    public int getAuditId() {
        return auditId;
    }
    
    public void setAuditId(int auditId) {
        this.auditId = auditId;
    }

    public String getActionDescription() {
        return actionDescription;
    }

    public void setActionDescription(String actionDescription) {
        this.actionDescription = actionDescription;
    }

    public LocalDateTime getActionTimestamp() {
        return actionTimestamp;
    }

    public void setActionTimestamp(LocalDateTime actionTimestamp) {
        this.actionTimestamp = actionTimestamp;
    }

}
