package com.fit.ws.resource.services;

import org.springframework.stereotype.Service;

import com.fit.ws.core.exceptions.XSSServletException;
import com.fit.ws.core.utils.XSSValidationUtils;

@Service
public class XSSValidationService {
	public boolean santize(String input) {
		if(!XSSValidationUtils.isValidURL(input)) {
			throw new XSSServletException("Error");
		}
		return true;
	}
}
