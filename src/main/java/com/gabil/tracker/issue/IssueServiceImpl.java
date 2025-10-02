package com.gabil.tracker.issue;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.gabil.tracker.common.IssueRequest;
import com.gabil.tracker.common.IssueResponse;
import com.gabil.tracker.common.IssueStatus;
import com.gabil.tracker.common.IssueUpdateRequest;
import com.gabil.tracker.common.error.BaseException;
import com.gabil.tracker.common.error.ErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IssueServiceImpl implements IssueService {

	private final IssueRepository issueRepository;
	private final IssueMapper issueMapper;
	
	@Override
	@Transactional
	public IssueResponse create(IssueRequest request) {
		var saved = issueRepository.save(issueMapper.toEntity(request));
		
		return issueMapper.toResponse(saved);
		
	}

	@Override
	@Transactional(readOnly = true)
	public IssueResponse getById(Long id) {
		var issue = issueRepository.findById(id).orElseThrow(() -> new BaseException(ErrorCode.ISSUE_NOT_FOUND,"Issue not found"));
		
		return issueMapper.toResponse(issue);
	}

	@Override
	@Transactional
	public Page<IssueResponse> list(String q, IssueStatus status, Long projectId, Pageable pageable) {
		
		return issueRepository
				.findAll(pageable)
				.map(issueMapper::toResponse);
	}

	@Override
	@Transactional
	public IssueResponse update(Long id, IssueUpdateRequest request) {
		
		Issue issue = issueRepository.findById(id).orElseThrow(() -> new BaseException(ErrorCode.ISSUE_NOT_FOUND,"Issue not found"));
		
		issueMapper.updateEntityFromDto(request, issue);
		
		var saved = issueRepository.save(issue);
		
		return issueMapper.toResponse(saved);
		
	}

	@Override
	@Transactional
	public void delete(Long id) {
		Issue issue = issueRepository.findById(id)
		        .orElseThrow(() -> new BaseException(ErrorCode.ISSUE_NOT_FOUND, "Issue not found"));
		    issueRepository.delete(issue);
		}
}
