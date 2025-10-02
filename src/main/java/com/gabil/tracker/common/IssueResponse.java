package com.gabil.tracker.common;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IssueResponse {
	
	private Long id;
	
	private String title;
	
	private IssueStatus status;
	
	private Long projectId;
	
	private Instant createdAt;
	
	private Instant updatedAt;
}
