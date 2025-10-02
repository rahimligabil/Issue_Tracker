package com.gabil.tracker.common;

import java.time.Instant;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectResponse {
	
	private Long id;

	private String name;
	
	private Instant createdAt;
}
