package com.fit.ws.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fit.ws.auth.dto.LoginByGoogle;
import com.fit.ws.auth.dto.LoginModel;
import com.fit.ws.auth.dto.SignupModel;
import com.fit.ws.auth.service.AuthenticationService;
import com.fit.ws.core.utils.ResponseAPI;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Authentication", description = "Authentication")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class AuthenticationController {
	private final AuthenticationService authenticationService;
	
	@PostMapping("/auth/register")
	public Object register(@RequestBody SignupModel signupModel) {
		ResponseAPI responseAPI = new ResponseAPI();
		responseAPI.setOk();
		responseAPI.setResutl(authenticationService.sigup(signupModel));
		return responseAPI.build();
	}
	
	@PostMapping("/auth/resgiter/other")
	public Object registerUserOther(@RequestBody LoginByGoogle loginByGoogle) {
		ResponseAPI responseAPI = new ResponseAPI();
		responseAPI.setOk();
		responseAPI.setResutl(authenticationService.sigupByGoogle(loginByGoogle));
		return responseAPI.build();
		
	}
	
	
	@PostMapping("/auth/login")
	public Object login(@RequestBody LoginModel loginModel) {
		ResponseAPI responseAPI = new ResponseAPI();
		responseAPI.setOk();
		responseAPI.setResutl(authenticationService.login(loginModel));
		return responseAPI.build();
	}
	
	@GetMapping("/auth/google")
	public Object loginByGoogle(@AuthenticationPrincipal OAuth2User auth2User) {
		ResponseAPI responseAPI = new ResponseAPI();
		if(auth2User == null) {
			responseAPI.setBad();
			responseAPI.setResutl(HttpStatus.UNAUTHORIZED);
		}
		
		LoginByGoogle loginByGoogle = new LoginByGoogle();
		loginByGoogle.setEmail(auth2User.getAttribute("email"));
		loginByGoogle.setName(auth2User.getAttribute("name"));
		loginByGoogle.setPicture(auth2User.getAttribute("picture"));
		
		responseAPI.setOk();
		responseAPI.setResutl(loginByGoogle);
		
		return responseAPI.build();
	}
	
	@GetMapping("refesh/checkToken")
	public Object checkToken() {
		ResponseAPI responseAPI = new ResponseAPI();
		responseAPI.setOk();
		responseAPI.setResutl(authenticationService.checkToken());
		responseAPI.build();
		return responseAPI;
	}
	
}
