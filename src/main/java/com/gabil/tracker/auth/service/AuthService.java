package com.gabil.tracker.auth.service;

import com.gabil.tracker.auth.dto.LoginRequest;
import com.gabil.tracker.auth.dto.RegisterRequest;

public interface AuthService {

    record Tokens(String access, long accessTtlSec,
                  String refresh, long refreshTtlSec) {}

    void register(RegisterRequest req);

    Tokens login(LoginRequest req);

    Tokens refresh(String refreshToken);

    void logout(String refreshToken, String accessToken, boolean allDevices);
}
