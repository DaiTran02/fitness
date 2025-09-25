package com.fit.ws.resource.dto.filter;

import lombok.Data;

@Data
public class PostFilter {
	private String idUser;
	private int skip = 0;
	private int limit = 5;
}	
