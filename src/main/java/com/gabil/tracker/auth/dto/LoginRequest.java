package com.gabil.tracker.auth.dto;

import java.io.Serializable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginRequest {

    @NotBlank(message = "Email zorunludur")
    @Email(message = "Email formatı geçerli olmalı")
    private String email; 

    @NotBlank(message = "Şifre zorunludur")
    @Size(min = 8, max = 128, message = "Şifre 8-128 karakter olmalı")
    private String password; 

    private Boolean rememberMe;
    private String deviceInfo; 
}
