package com.fit.ws.auth.service.impl;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.fit.ws.auth.service.JwtService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtServiceImpl implements JwtService{
	private String jwtSigningKey = "413F4428472B4B6250655368566D5970337336763979244226452948404D6351";

	@Override
	public String extractUserName(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	@Override
	public String generateToken(UserDetails userDetails) {
		return generateToken(new HashMap<>(),userDetails);
	}
	

	@Override
	public boolean isTokenValid(String token, UserDetails userDetails) {
		final String userName = extractUserName(token);
		return (userName.equals(userDetails.getUsername())) && !isTokenExpired(token);
	}
	
	private String generateToken(Map<String, Object> extracClaims,UserDetails userDetails) {
		return Jwts.builder()
					.setSubject(userDetails.getUsername())
					.claim("roles", userDetails.getAuthorities())
					.setIssuedAt(new Date())
					.setExpiration(new Date(System.currentTimeMillis() + 86400000))
					.signWith(getSigningKey(),SignatureAlgorithm.HS256)
					.compact();
	}
	
	@Override
	public String generateToken(Authentication authentication) {
		System.out.println("What is this: "+authentication);
		return Jwts.builder().setSubject(authentication.getName())
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + 86400000))
				.signWith(getSigningKey(),SignatureAlgorithm.HS256)
				.compact();
	}
	
	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}
	
	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}
	
	private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
		final Claims claims = extractAllClaims(token);
		return claimsResolvers.apply(claims);
	}
	
	private Claims extractAllClaims(String token) throws ExpiredJwtException{
		return Jwts.parserBuilder()
					.setSigningKey(getSigningKey())
					.build()
					.parseClaimsJws(token)
					.getBody();
	}
	
	private Key getSigningKey() {
		byte[] keyBytes = Decoders.BASE64.decode(jwtSigningKey);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	@Override
	public boolean isTokenValid(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token);
			return true;
		} catch (JwtException | IllegalArgumentException e) {
			return false;
		}
	}
}
