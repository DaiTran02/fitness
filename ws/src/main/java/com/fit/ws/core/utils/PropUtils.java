package com.fit.ws.core.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class PropUtils {
	@Autowired
	private Environment env;
	
	public String getDomainAllow() {
		return env.getProperty("spring.security.allow.cors.domain").toString();
	}
	
	public String getStoresFolderPath() {
		return env.getProperty("core.storages.folder.path");
	}
	
}
