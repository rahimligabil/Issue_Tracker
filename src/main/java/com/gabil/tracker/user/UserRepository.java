package com.gabil.tracker.user;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,UUID>{
	
	   @EntityGraph(attributePaths = {"roles"})
	   Optional<User> findWithRolesByEmailIgnoreCase(String email);

	   boolean existsByEmailIgnoreCase(String email);
	   
	   Optional<User> findByEmail(String email);
}
