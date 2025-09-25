package com.fit.ws.core.exceptions;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fit.ws.core.utils.ResponseAPI;

import io.jsonwebtoken.ExpiredJwtException;

@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(value = RuntimeException.class)
	public ResponseEntity<ResponseAPI> handlerRuntimeException(RuntimeException exception){
		ResponseAPI reponseApi = new ResponseAPI();
		reponseApi.setBad();
		
		return ResponseEntity.badRequest().body(reponseApi);
	}
	
	@ExceptionHandler(value = AppException.class)
	public Object handlerRuntimeException(AppException appException){
		ResponseAPI reponseApi = new ResponseAPI();
		ErrorCode errorCode = appException.getErrorCode();
		reponseApi.setStatus(HttpStatus.BAD_REQUEST);
		reponseApi.setMessage(errorCode.getMessage());
		
		return reponseApi.build();
	}
	
	@ExceptionHandler(value = ExpiredJwtException.class)
	public Object handleExpiredJwt() {
		ResponseAPI responseAPI = new ResponseAPI();
		
		responseAPI.setBad();
		responseAPI.setMessage("Hêt hạn token");
		
		return responseAPI.build();
	}
	
	@ExceptionHandler(value = FileNotFoundException.class)
	public Object handleFileNotFound() {
		ResponseAPI responseAPI = new ResponseAPI();
		
		responseAPI.setBad();
		responseAPI.setResutl("Không tìm thấy file");
		
		return responseAPI.build();
	}
	
	@ExceptionHandler(value = IOException.class)
	public Object handleIOException() {
		ResponseAPI responseAPI = new ResponseAPI();
		
		responseAPI.setBad();
		responseAPI.setResutl("Lỗi! Vui lòng liên hệ quản trị viên");
		
		return responseAPI.build();
	}
	
	@ExceptionHandler(value = Exception.class)
	public Object handleException() {
		ResponseAPI responseAPI = new ResponseAPI();
		responseAPI.setBad();
		responseAPI.setResutl("Lỗi! Vui lòng liên hệ quản trị viên");
		return responseAPI.build();
	}

}
