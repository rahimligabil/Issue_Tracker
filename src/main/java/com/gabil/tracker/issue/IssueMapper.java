package com.gabil.tracker.issue;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.gabil.tracker.common.IssueRequest;
import com.gabil.tracker.common.IssueResponse;
import com.gabil.tracker.common.IssueUpdateRequest;
import com.gabil.tracker.common.ProjectUpdateRequest;
import com.gabil.tracker.project.Project;

@Mapper(componentModel = "spring")
public interface IssueMapper {
	
	public Issue toEntity(IssueRequest request);
	
	public IssueResponse toResponse(Issue entity);
	
	  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	  void updateEntityFromDto(IssueUpdateRequest dto, @MappingTarget Issue entity);

}
