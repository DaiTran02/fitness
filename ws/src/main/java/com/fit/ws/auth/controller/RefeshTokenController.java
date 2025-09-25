package com.fit.ws.auth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fit.ws.core.utils.ResponseAPI;

@RestController()
@RequestMapping("/api/v1/")
public class RefeshTokenController {
	@GetMapping("check-token")
	public Object checkToken() {
		ResponseAPI responseAPI = new ResponseAPI();
		responseAPI.setOk();
		return responseAPI.build();
	}
}
