package com.gabil.tracker.security;

import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.gabil.tracker.user.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParserBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtService {
	
	   private final Key secretKey = Keys.hmacShaKeyFor("supersecretkeysupersecretkey123456".getBytes());

	   private final String issuer = "gabil-tracker";
	   private final long accessTokenTtlSeconds = 15 * 60;
	   private final long refreshTokenTtlSeconds = 15 * 60 * 96 * 30;

	public String generateAccessToken(User user) {
		 Instant now = Instant.now();
	     Instant expiry = now.plusSeconds(accessTokenTtlSeconds);

	        
	        
		return Jwts.builder()
		.setSubject(user.getId()
				.toString())
				.setIssuer("gabil-tracker")
				.setIssuedAt(Date.from(now))
				.setExpiration(Date.from(expiry))
				.setId(UUID.randomUUID().toString())
				.claim("token_type", "access")
				.claim("roles",List.of("User"))
				.signWith(secretKey, SignatureAlgorithm.HS256)
				.compact();

	}
	
	public String generateRefreshToken(User user) {
		 Instant now = Instant.now();
	     Instant expiry = now.plusSeconds(refreshTokenTtlSeconds);

	        
	        
		return Jwts.builder()
		.setSubject(user.getId()
				.toString())
				.setIssuer("gabil-tracker")
				.setIssuedAt(Date.from(now))
				.setExpiration(Date.from(expiry))
				.setId(UUID.randomUUID().toString())
				.claim("token_type", "refresh")
				.signWith(secretKey, SignatureAlgorithm.HS256)
				.compact();

	}
	
	
	public boolean validateAccessToken(String token) {
		Claims claim = parseAndVerify(token);
		boolean result = checkTokenType(claim,"access");
		
		return result;
	}
	
	public boolean validateRefreshToken(String token) {
		Claims claim = parseAndVerify(token);
		boolean result = checkTokenType(claim,"refresh");
		
		return result;
	}
	
	  public boolean isTokenValid(String token, UserDetails userDetails) {
	        final String username = extractSubject(token);
	        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
	    }
	
	
	
	private boolean checkTokenType(Claims claims, String expected) {
		String type = claims.get("token_type", String.class);
		if(type == null || !type.equals(expected)) {
			return false;
		}
		return true;
	}
	
	
	
	 public String extractSubject(String token) {
	        return parseAndVerify(token).getSubject();             
	   }

	    private Claims parseAndVerify(String token) {
	    	JwtParserBuilder pb = Jwts.parserBuilder().requireIssuer(issuer).setSigningKey(secretKey);
	    	
	    	return pb.build().parseClaimsJws(token).getBody();
	}

		public List<String> extractRoles(String token) {
	        Claims c = parseAndVerify(token);
	        Object val = c.get("roles");
	        return (val instanceof List<?> l)
	                ? l.stream().map(Object::toString).toList()
	                : List.of();                                      
	    }

	    public String extractTokenType(String token) {
	        return parseAndVerify(token).get("token_type", String.class);
	    }

	    public String extractJti(String token) {
	        return parseAndVerify(token).getId();                     
	    }

	    public Instant extractExpiration(String token) {
	        Date d = parseAndVerify(token).getExpiration();            
	        return d.toInstant();
	    }
	    
	    public boolean isTokenExpired(String token)     { return extractExpiration(token).isBefore(Instant.now()); }

}
