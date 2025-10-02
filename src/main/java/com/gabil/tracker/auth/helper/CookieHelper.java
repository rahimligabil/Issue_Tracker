package com.gabil.tracker.auth.helper;

import java.util.Arrays;
import java.util.Optional;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CookieHelper {
	 public static final String REFRESH_COOKIE = "REFRESH_TOKEN";
	 public static final String REFRESH_PATH   = "/auth/refresh";

	 public static void writeRefresh(HttpServletResponse res,String token,long maxAge,boolean secure) {
		 
		 ResponseCookie response = ResponseCookie.from(REFRESH_COOKIE, token)
				 .httpOnly(true)
				 .secure(secure)
				 .sameSite("Strict")
				 .path(REFRESH_PATH)
				 .maxAge(maxAge)
				 .build();
		 
		 res.addHeader(HttpHeaders.SET_COOKIE, response.toString());
	 }
	 
	 
	 public static void clearRefresh(HttpServletResponse res, boolean secure) {
	        ResponseCookie c = ResponseCookie.from(REFRESH_COOKIE, "")
	                .httpOnly(true)
	                .secure(secure)
	                .sameSite("Strict")
	                .path(REFRESH_PATH)
	                .maxAge(0)
	                .build();
	        res.addHeader(HttpHeaders.SET_COOKIE, c.toString());
	    }

	 
	    public static Optional<String> readRefresh(HttpServletRequest req) {
	        if (req.getCookies() == null) return Optional.empty();
	        return Arrays.stream(req.getCookies())
	                .filter(c -> REFRESH_COOKIE.equals(c.getName()))
	                .map(jakarta.servlet.http.Cookie::getValue)
	                .findFirst();
	    }
}
