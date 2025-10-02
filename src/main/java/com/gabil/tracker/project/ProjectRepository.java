package com.gabil.tracker.project;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project,Long>,JpaSpecificationExecutor<Project>
{
	public Optional<Project> findByName(String name);
	public Page<Project> findAll(Pageable pageable);
}
