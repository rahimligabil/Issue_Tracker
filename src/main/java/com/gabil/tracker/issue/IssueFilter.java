package com.gabil.tracker.issue;

import java.time.Instant;
import com.gabil.tracker.common.IssueStatus;

public class IssueFilter {
    private Long projectId;          
    private IssueStatus status;      
    private String q;               
    private Instant createdAfter;    
    private Instant createdBefore;   

    public Long getProjectId() { return projectId; }
    public void setProjectId(Long projectId) { this.projectId = projectId; }
    public IssueStatus getStatus() { return status; }
    public void setStatus(IssueStatus status) { this.status = status; }
    public String getQ() { return q; }
    public void setQ(String q) { this.q = q; }
    public Instant getCreatedAfter() { return createdAfter; }
    public void setCreatedAfter(Instant createdAfter) { this.createdAfter = createdAfter; }
    public Instant getCreatedBefore() { return createdBefore; }
    public void setCreatedBefore(Instant createdBefore) { this.createdBefore = createdBefore; }
}
