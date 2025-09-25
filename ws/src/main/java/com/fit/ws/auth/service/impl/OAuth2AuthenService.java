package com.fit.ws.auth.service.impl;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fit.ws.auth.dto.LoginByGoogle;
import com.fit.ws.auth.service.AuthenticationService;
import com.fit.ws.auth.service.JwtService;
import com.fit.ws.core.utils.PropUtils;
import com.fit.ws.user.AuthProvider;
import com.fit.ws.user.model.UserModel;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuth2AuthenService extends SimpleUrlAuthenticationSuccessHandler{
	
	@Autowired
	private PropUtils propUtils;
	@Autowired
	private AuthenticationService authenticationService;
	@Autowired
	private JwtService jwtService;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, 
            HttpServletResponse response, 
            Authentication authentication) throws IOException {
		 // Get user information from OAuth2User
        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
        
        // Extract user details
        String email = oauth2User.getAttribute("email");
        String name = oauth2User.getAttribute("name");
        String picture = oauth2User.getAttribute("picture");
        String providerId = oauth2User.getAttribute("sub");
        
        
        LoginByGoogle loginByGoogle = new LoginByGoogle();
        loginByGoogle.setEmail(email);
        loginByGoogle.setName(name);
        loginByGoogle.setPicture(picture);
        loginByGoogle.setProviderId(providerId);
        
        UserModel userModel = authenticationService.loginByGoogle(loginByGoogle, AuthProvider.GOOGLE);
        
		String token = jwtService.generateToken(userModel);
		
        String redirectUrl = String.format("%s/oauth2/redirect?token=%s&email=%s&name=%s&picture=%s&id=%s",
        		propUtils.getDomainAllow(),
                token,
                URLEncoder.encode(userModel.getFullName(), StandardCharsets.UTF_8),
                URLEncoder.encode(userModel.getFullName(), StandardCharsets.UTF_8),
                URLEncoder.encode(userModel.getAvatar() != null ? userModel.getAvatar() : "", StandardCharsets.UTF_8),
                URLEncoder.encode(userModel.getIdAsString(), StandardCharsets.UTF_8));
        
		
		response.sendRedirect(redirectUrl);
	}

}
