package com.gabil.tracker.security;
import java.io.IOException;
import java.util.Locale;
import java.util.UUID;

import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.gabil.tracker.user.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends  OncePerRequestFilter {

	private final JwtService jwtService;
	private final UserRepository userRepository;
	
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
			
		if(HttpMethod.OPTIONS.matches(request.getMethod())) {
			filterChain.doFilter(request, response);
			return;
		}
		
	       String path = request.getServletPath();
           if (isWhitelisted(path)) {
               filterChain.doFilter(request, response);
               return;
           }
           
           String authHeader = request.getHeader("Authorization");
           if(authHeader == null || !authHeader.toLowerCase(Locale.ENGLISH).startsWith("bearer ")) {
        	   filterChain.doFilter(request, response);
        	   return;
           }
           
           String token = authHeader.substring(7).trim();
           if (token.isEmpty()) {
               filterChain.doFilter(request, response);
               return;
           }
           
           String email = jwtService.extractSubject(token);
           UUID id  = UUID.fromString(email);
           
           Authentication currentAuth = SecurityContextHolder.getContext().getAuthentication();
           
           if(currentAuth == null && id != null) {
        	   UserDetails userDetails = userRepository.findById(id)
        		        .orElseThrow(() -> new UsernameNotFoundException("User not found"));  
        	   
        	   if(userDetails != null && jwtService.isTokenValid(token, userDetails)) {
        		   UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
        		   auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        		   
        		   
        		   SecurityContext ctx = SecurityContextHolder.createEmptyContext();
        		   ctx.setAuthentication(auth);
        		   SecurityContextHolder.setContext(ctx);

        	   
        	   
        	   
        	   }
        	   }
           filterChain.doFilter(request, response);
           
	}
	
	  private boolean isWhitelisted(String path) {
	        return path.startsWith("/auth/") ||
	               path.startsWith("/swagger-ui") ||
	               path.startsWith("/v3/api-docs") ||
	               path.startsWith("/actuator/health");
	    }

}
