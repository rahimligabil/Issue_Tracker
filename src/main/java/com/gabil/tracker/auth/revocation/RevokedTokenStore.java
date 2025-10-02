package com.gabil.tracker.auth.revocation;

import java.time.Instant;

public interface RevokedTokenStore {

   void revoke(String jti, Instant expiresAt);

    
    boolean isRevoked(String jti);
}
