package com.gabil.tracker.auth.service;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.gabil.tracker.auth.dto.LoginRequest;
import com.gabil.tracker.auth.dto.RegisterRequest;
import com.gabil.tracker.auth.revocation.RevokedTokenStore;
import com.gabil.tracker.security.JwtService;
import com.gabil.tracker.user.User;
import com.gabil.tracker.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authManager;
	private final JwtService jwtService;
    private final RevokedTokenStore revoked;
	
	@Override
	public void register(RegisterRequest req) {
		String email = req.getEmail();
		
		if(userRepository.existsByEmailIgnoreCase(email)) {
			throw new IllegalArgumentException("Email already in use");
		}
		User user = User.builder()
				.email(req.getEmail())
				.password(passwordEncoder.encode(req.getPassword()))
				.displayName(req.getFullName())
				.build();
		
		userRepository.save(user);
	}

	@Override
	public Tokens login(LoginRequest req) {
		String email = req.getEmail().trim().toLowerCase();

	    Authentication auth = authManager.authenticate(
	        new UsernamePasswordAuthenticationToken(email, req.getPassword())
	    );

	   
	    User user = (User) auth.getPrincipal(); 

	    return issueTokens(user);
	}

	private Tokens issueTokens(User user) {
		String access = jwtService.generateAccessToken(user);
        String refresh = jwtService.generateRefreshToken(user);

        long accessTtl = secondsUntil(jwtService.extractExpiration(access));
        long refreshTtl = secondsUntil(jwtService.extractExpiration(refresh));

        return new Tokens(access, accessTtl, refresh, refreshTtl);

	}

	@Override
	public Tokens refresh(String refreshToken) {
		  if (!jwtService.validateRefreshToken(refreshToken)) {
	            throw new IllegalArgumentException("Invalid refresh token");
	        }
	        String oldJti = jwtService.extractJti(refreshToken);
	        if (revoked.isRevoked(oldJti)) {
	            throw new IllegalStateException("Refresh token already revoked");
	        }
	        UUID userId = UUID.fromString(jwtService.extractSubject(refreshToken));
	        User user = userRepository.findById(userId)
	                .orElseThrow(() -> new IllegalArgumentException("User not found"));

	        revoked.revoke(oldJti, jwtService.extractExpiration(refreshToken));
	        return issueTokens(user);
	    }
	

	@Override
	public void logout(String refreshToken, String accessToken, boolean allDevices) {
		if (refreshToken != null && !refreshToken.isBlank()) {
	        revoked.revoke(jwtService.extractJti(refreshToken),
	                       jwtService.extractExpiration(refreshToken));
	    }
	    if (accessToken != null && !accessToken.isBlank()) {
	        revoked.revoke(jwtService.extractJti(accessToken),
	                       jwtService.extractExpiration(accessToken));
	    }
	}
	
	 private long secondsUntil(Instant exp) {
	        long s = Duration.between(Instant.now(), exp).getSeconds();
	        return Math.max(s, 0);
	    }

}
