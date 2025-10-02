package com.gabil.tracker.auth.revocation;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

@Component
public class InMemoryRevokedTokenStore implements RevokedTokenStore {
	
	private final Map<String, Instant> revoked = new ConcurrentHashMap<>();
	
	 public void revoke(String jti, Instant exp){ 
		 revoked.put(jti, exp); 
		 }
	 
	 
	  public boolean isRevoked(String jti){
	    Instant e = revoked.get(jti);
	    if (e == null) return false;
	    if (e.isBefore(Instant.now())) { 
	    	revoked.remove(jti); 
	    	return false; 
	    	
	    } 
	    return true;
	  }
	
}
