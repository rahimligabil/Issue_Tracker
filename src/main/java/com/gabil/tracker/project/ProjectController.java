package com.gabil.tracker.project;

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
import org.springframework.web.bind.annotation.RestController;
import com.gabil.tracker.common.ProjectRequest;
import com.gabil.tracker.common.ProjectResponse;
import com.gabil.tracker.common.ProjectUpdateRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/projects")
public class ProjectController {
	
	private final ProjectService projectService;
	
	
	@PostMapping("/create")
	public ResponseEntity<ProjectResponse> createProject(@RequestBody ProjectRequest projectRequest){
		var response = projectService.create(projectRequest);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@GetMapping("/get/{id}")
	public ResponseEntity<ProjectResponse> getProjectById(@PathVariable Long id){
		var response = projectService.getById(id);
		
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/get/list")
	public ResponseEntity<Page<ProjectResponse>> list(Pageable pageable){
		   Page<ProjectResponse> result = projectService.listAll(pageable);
		   return ResponseEntity.ok(result);
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<ProjectResponse> update(
	        @PathVariable Long id,
	        @RequestBody ProjectUpdateRequest request) {

	    ProjectResponse response = projectService.update(id, request);
	    return ResponseEntity.ok(response); 
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> deleteById(@PathVariable Long id){
		
		projectService.delete(id);
		
		return ResponseEntity.noContent().build();
	}

	
	
	

}
