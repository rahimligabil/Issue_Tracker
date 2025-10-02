package com.gabil.tracker.common;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IssueRequest {

	@NotBlank(message = "Issue title cannot be blank")
	@Size(min = 3, max = 100, message = "Issue title must be between 3 and 100 characters")
	private String title;

	private IssueStatus status = IssueStatus.OPEN;

	@Size(max = 500, message = "Description cannot be longer than 500 characters")
	private String description;
}
