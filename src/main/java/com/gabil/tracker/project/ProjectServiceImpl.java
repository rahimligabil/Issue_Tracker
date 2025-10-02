package com.gabil.tracker.project;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gabil.tracker.common.ProjectRequest;
import com.gabil.tracker.common.ProjectResponse;
import com.gabil.tracker.common.ProjectUpdateRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

	private final ProjectRepository projectRepository;
	private final ProjectMapper projectMapper;
	
	@Override
	@Transactional
	public ProjectResponse create(ProjectRequest request) {
		
		
		var project = projectMapper.toEntity(request);
		
		var saved = projectRepository.save(project);
		
		return projectMapper.toResponse(saved);
	}

	@Override
	@Transactional(readOnly = true)
	public ProjectResponse getById(Long id) {
		Project project =  projectRepository.findById(id).orElseThrow();
		
		return projectMapper.toResponse(project);
	}

	@Transactional(readOnly = true)
	public Page<ProjectResponse> listAll(Pageable pageable) {
	  return projectRepository.findAll(pageable)     
	                          .map(projectMapper::toResponse);

	}
	@Override
	@Transactional
	public ProjectResponse update(Long id, ProjectUpdateRequest request) {
		Project project = projectRepository.findById(id).orElseThrow();
		
		projectMapper.updateEntityFromDto(request, project);
		
		projectRepository.save(project);
		
		return projectMapper.toResponse(project);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		if(id < 0) {
			throw new RuntimeException();
		}
		projectRepository.deleteById(id);
	}

}
