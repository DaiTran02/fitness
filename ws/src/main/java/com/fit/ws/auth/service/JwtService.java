package com.fit.ws.auth.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
	String extractUserName(String token);
	String generateToken(UserDetails userDetails);
	String generateToken(Authentication authentication);
	boolean isTokenValid(String token, UserDetails userDetails);
	boolean isTokenValid(String token);
}
