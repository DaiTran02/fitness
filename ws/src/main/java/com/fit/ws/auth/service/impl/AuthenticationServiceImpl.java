package com.fit.ws.auth.service.impl;

import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fit.ws.auth.dto.LoginByGoogle;
import com.fit.ws.auth.dto.LoginModel;
import com.fit.ws.auth.dto.OutputLoginModel;
import com.fit.ws.auth.dto.SignupModel;
import com.fit.ws.auth.service.AuthenticationService;
import com.fit.ws.auth.service.JwtService;
import com.fit.ws.core.exceptions.AppException;
import com.fit.ws.core.exceptions.ErrorCode;
import com.fit.ws.user.AuthProvider;
import com.fit.ws.user.Role;
import com.fit.ws.user.model.UserModel;
import com.fit.ws.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
	private final UserService userService;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;


	@Override
	public String sigup(SignupModel signupModel) {
		var user = UserModel.builder()
				.fullName(signupModel.getFullname())
				.username(signupModel.getUsername())
				.password(passwordEncoder.encode(signupModel.getPassword()))
				.role(Role.USER).build();
		userService.create(user);
		return "Thành công";
	}

	@Override
	public OutputLoginModel login(LoginModel loginModel) {
		var user = userService.findByUsername(loginModel.getUsername()).orElseThrow(()->new AppException(ErrorCode.USER_NOT_FOUND));
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginModel.getUsername(), loginModel.getPassword()));
		var jwt = jwtService.generateToken(user);
		return OutputLoginModel.builder().fullname(user.getFullName())
				.username(user.getUsername()).token(jwt).id(user.getIdAsString())
				.avatar(user.getAvatar()).build();
	}

	@Override
	public boolean checkToken() {
		return true;
	}

	@Override
	public UserModel loginByGoogle(LoginByGoogle userInfo, AuthProvider authProvider) {
		Optional<UserModel> user = userService.findByProderId(userInfo.getProviderId());
		UserModel userTemp;
		if(user.isPresent()) {
			userTemp = user.get();
			userTemp.setFullName(userInfo.getName());
			return userService.update(userTemp);
		}else {
			userTemp = UserModel.builder().fullName(userInfo.getName())
					.username(userInfo.getEmail()).email(userInfo.getEmail()).avatar(userInfo.getPicture())
					.role(Role.USER).provider(authProvider).providerId(userInfo.getProviderId()).build();
			return userService.create(userTemp);
		}
	}

	@Override
	public UserModel sigupByGoogle(LoginByGoogle user) {
		var createUser = UserModel.builder()
				.fullName(user.getName())
				.username(user.getUsername())
				.password(passwordEncoder.encode(user.getPassword()))
				.avatar(user.getPicture())
				.email(user.getEmail())
				.provider(AuthProvider.GOOGLE)
				.providerId(user.getProviderId())
				.role(Role.USER).build();
		return userService.create(createUser);
	}


}
