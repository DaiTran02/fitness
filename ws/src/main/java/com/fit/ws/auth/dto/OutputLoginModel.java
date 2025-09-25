package com.fit.ws.auth.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class OutputLoginModel {
	private String id;
	private String fullname;
	private String username;
	private String token;
	private String avatar;
}
