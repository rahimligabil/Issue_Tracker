package com.gabil.tracker.issue;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gabil.tracker.common.IssueRequest;
import com.gabil.tracker.common.IssueResponse;
import com.gabil.tracker.common.IssueStatus;
import com.gabil.tracker.common.IssueUpdateRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/issues")
public class IssueController {
	
	private final IssueService issueService;
		
	
	@PostMapping("/create")
	public ResponseEntity<IssueResponse> createIssue(@RequestBody IssueRequest issueRequest) {
		IssueResponse response =  issueService.create(issueRequest);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
		
	}
	
	@GetMapping("/get/{id}")
	public ResponseEntity<IssueResponse> getIssueById(@PathVariable Long id) {
		IssueResponse response = issueService.getById(id);
		
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/get/list")
	public ResponseEntity<Page<IssueResponse>> list(
	        @RequestParam(required = false) String q,
	        @RequestParam(required = false) IssueStatus status,
	        @RequestParam(required = false) Long projectId,
	        Pageable pageable) {

	    Page<IssueResponse> result = issueService.list(q, status, projectId, pageable);
	    return ResponseEntity.ok(result);
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<IssueResponse> update(
	        @PathVariable Long id,
	        @RequestBody IssueUpdateRequest request) {

	    IssueResponse response = issueService.update(id, request);
	    return ResponseEntity.ok(response); 
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> deleteById(@PathVariable Long id){
		
		issueService.delete(id);
		
		return ResponseEntity.noContent().build();
	}

	
	
	
}
