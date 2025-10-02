package com.gabil.tracker.issue;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import com.gabil.tracker.common.IssueRequest;
import com.gabil.tracker.common.IssueResponse;
import com.gabil.tracker.common.IssueUpdateRequest;
import com.gabil.tracker.common.IssueStatus;

public interface IssueService {

    IssueResponse create(IssueRequest request);

    IssueResponse getById(Long id);

    Page<IssueResponse> list(String q, IssueStatus status, Long projectId, Pageable pageable);

    IssueResponse update(Long id, IssueUpdateRequest request);

    void delete(Long id);
}
