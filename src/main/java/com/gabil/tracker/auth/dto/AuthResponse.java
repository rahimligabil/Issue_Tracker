package com.gabil.tracker.auth.dto;


import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponse {

    private String accessToken; 
    private long expiresIn;     
    private UserSummary user;  
}
