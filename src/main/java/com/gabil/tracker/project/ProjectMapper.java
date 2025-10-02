package com.gabil.tracker.project;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.gabil.tracker.common.ProjectRequest;
import com.gabil.tracker.common.ProjectResponse;
import com.gabil.tracker.common.ProjectUpdateRequest;

@Mapper(componentModel = "spring")
public interface ProjectMapper {
	  	ProjectResponse toResponse(Project project);
	    Project toEntity(ProjectRequest request);
	    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	    void updateEntityFromDto(ProjectUpdateRequest dto, @MappingTarget Project entity);
}
