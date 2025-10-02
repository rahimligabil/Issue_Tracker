package com.gabil.tracker.issue;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.gabil.tracker.project.Project;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Long>,JpaSpecificationExecutor<Issue> {

	List<Issue> findByProjectId(Long projectId);
	
	public Page<Issue> findAll(Pageable pageable);
}
