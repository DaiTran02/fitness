package com.fit.ws.auth.dto;

import lombok.Data;

@Data
public class SignupModel {
	private String fullname;
	private String username;
	private String password;
	private String email;
	private String avatar;
}
