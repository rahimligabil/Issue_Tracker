package com.gabil.tracker.common;

import java.util.Date;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectRequest {

	@NotBlank(message = "Name title cannot be blank")
	@Size(min = 3, max = 100, message = "Name title must be between 3 and 100 characters")
	private String title;
	private String name;
	
}
