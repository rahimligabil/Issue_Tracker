package com.gabil.tracker.auth.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Set;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSummary {

    private String id;           
    private String email;       
    private String fullName;     
    private Set<String> roles;   
    private Instant createdAt;  
}
