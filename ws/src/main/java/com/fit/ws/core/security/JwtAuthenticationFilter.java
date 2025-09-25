package com.fit.ws.core.security;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fit.ws.auth.service.JwtService;
import com.fit.ws.core.exceptions.AppException;
import com.fit.ws.core.exceptions.ErrorCode;
import com.fit.ws.user.service.UserService;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter{
	private final JwtService jwtService;
	private final UserService userService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String authHeader = request.getHeader("Authorization");
		String jwt;
		String username;
		
		if(StringUtils.isEmpty(authHeader) || !authHeader.startsWith("Bearer")) {
			filterChain.doFilter(request, response);
			return;
		}
		
		jwt = authHeader.substring(7);
		try {
			username = jwtService.extractUserName(jwt);
			if(StringUtils.isNoneEmpty(username) && SecurityContextHolder.getContext().getAuthentication() == null) {
				UserDetails userDetails = userService.checkUserDetail().loadUserByUsername(username);
				if(jwtService.isTokenValid(jwt, userDetails)) {
					SecurityContext context = SecurityContextHolder.createEmptyContext();
					UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
					authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					context.setAuthentication(authToken);
					SecurityContextHolder.setContext(context);
				}
			}
			filterChain.doFilter(request, response);
		} catch (ExpiredJwtException e) {
//			filterChain.doFilter(request, response);
			throw new AppException(ErrorCode.INVALID_TOKEN);
		}
		
	}

}
