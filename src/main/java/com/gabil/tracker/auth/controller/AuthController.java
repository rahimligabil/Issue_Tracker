package com.gabil.tracker.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gabil.tracker.auth.dto.LoginRequest;
import com.gabil.tracker.auth.dto.RegisterRequest;
import com.gabil.tracker.auth.dto.TokenResponse;
import com.gabil.tracker.auth.helper.CookieHelper;
import com.gabil.tracker.auth.service.AuthService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
	
	private final AuthService authService;
	
	@PostMapping("/register")
	ResponseEntity<Void> register(@Valid @RequestBody RegisterRequest req ){
		authService.register(req);
		
		return ResponseEntity.status(201).build();
	}
	
	@PostMapping("/login")
	ResponseEntity<TokenResponse> register(@Valid @RequestBody LoginRequest req, HttpServletResponse res ){
		var t = authService.login(req);
		CookieHelper.writeRefresh(res, t.refresh(),t.refreshTtlSec(), true);
		
		return ResponseEntity.ok(new TokenResponse(t.access(), t.accessTtlSec()));
	}
	
	 @PostMapping("/refresh")
	    public ResponseEntity<TokenResponse> refresh(HttpServletRequest req, HttpServletResponse res) {
	        var refreshOpt = CookieHelper.readRefresh(req);
	        if (refreshOpt.isEmpty()) {
	            return ResponseEntity.status(401).build();
	        }
	        var t = authService.refresh(refreshOpt.get());
	        CookieHelper.writeRefresh(res, t.refresh(), t.refreshTtlSec(), /*secure=*/true);
	        return ResponseEntity.ok(new TokenResponse(t.access(), t.accessTtlSec()));
	    }
	
	 
	 @PostMapping("/logout")
	    public ResponseEntity<Void> logout(HttpServletRequest req,
	                                       HttpServletResponse res,
	                                       @RequestHeader(value = "Authorization", required = false) String authHeader,
	                                       @RequestParam(defaultValue = "false") boolean allDevices) {

	        String refresh = CookieHelper.readRefresh(req).orElse(null);
	        String access  = (authHeader != null && authHeader.toLowerCase().startsWith("bearer "))
	                ? authHeader.substring(7).trim()
	                : null;

	        authService.logout(refresh, access, allDevices);
	        CookieHelper.clearRefresh(res, /*secure=*/true);
	        return ResponseEntity.noContent().build();       
	    }
	
	
}
