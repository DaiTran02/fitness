package com.fit.ws.core.utils;

import java.io.Serializable;

import org.bson.Document;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseAPI implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Document document = new Document();
	private HttpStatus status = HttpStatus.OK;
	
	public void setStatus(HttpStatus status) {
		this.status = status;
		this.document.put("status", status.value());
	}
	
	public void setTotal(Object resutl) {
		this.document.put("total", resutl);
	}
	
	public void setResutl(Object result) {
		this.document.put("result", result);
	}
	
	public void setExpand(Object expand) {
		this.document.put("expand", expand);
	}
	
	public void setMessage(Object message) {
		this.document.put("message", message);
	}
	
	public void setError(Object error) {
		this.document.put("error", error);
	}
	
	public void setOk() {
		this.document.put("status", HttpStatus.OK.value());
		this.document.put("message", "Thành công");
	}
	
	public void setBad() {
		status = HttpStatus.BAD_REQUEST;
		this.document.put("status", status.value());
		this.document.put("message", "Không thành công");
	}
	
	public Object build() {
		return ResponseEntity.status(status).body(document);
	}
	
	public Object buildToCache() {
		return this.document;
	}

}
