package com.gabil.tracker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.gabil.tracker.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {
	
	private final UserRepository userRepository;

	
	@Bean
	UserDetailsService userDetailsService() {
		return email -> userRepository.findWithRolesByEmailIgnoreCase(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
	}
}
