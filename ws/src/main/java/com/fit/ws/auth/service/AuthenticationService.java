package com.fit.ws.auth.service;


import com.fit.ws.auth.dto.LoginByGoogle;
import com.fit.ws.auth.dto.LoginModel;
import com.fit.ws.auth.dto.OutputLoginModel;
import com.fit.ws.auth.dto.SignupModel;
import com.fit.ws.user.AuthProvider;
import com.fit.ws.user.model.UserModel;

public interface AuthenticationService {
	String sigup(SignupModel signupModel);
	UserModel sigupByGoogle(LoginByGoogle user);
	UserModel loginByGoogle(LoginByGoogle userInfo, AuthProvider authProvider);
	OutputLoginModel login(LoginModel loginModel);
	boolean checkToken();
}
