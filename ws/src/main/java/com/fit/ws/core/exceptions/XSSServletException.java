package com.fit.ws.core.exceptions;

public class XSSServletException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	public XSSServletException() {

	}
	
	public XSSServletException(String message) {
		super(message);
	}

}
