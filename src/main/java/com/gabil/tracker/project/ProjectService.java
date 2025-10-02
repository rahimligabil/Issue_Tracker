package com.gabil.tracker.project;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gabil.tracker.common.ProjectRequest;
import com.gabil.tracker.common.ProjectResponse;
import com.gabil.tracker.common.ProjectUpdateRequest;

public interface ProjectService {
	   ProjectResponse create(ProjectRequest request);
	   ProjectResponse getById(Long id);
	   Page<ProjectResponse> listAll(Pageable pageable); 
	   ProjectResponse update(Long id, ProjectUpdateRequest request);
	   void delete(Long id);
}
